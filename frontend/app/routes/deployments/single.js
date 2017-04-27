import Ember from 'ember';

const { Route, inject, RSVP } = Ember;

/**
 * Loads all necessary data for any visualisation types
 *
 * @class SingleDeploymentsRoute
 * @extends Ember.Route
 * @public
 */
export default Route.extend({
  session: inject.service(), // loads services/session.js
  changelogStream: inject.service(),
  flashMessages: inject.service(), // from addon

  /**
   * Renders the default template and global actions. Called by Ember
   *
   * @method renderTemplate
   * @private
   */
  renderTemplate() {
    this.render(); // default template

    this.render('deployments.globalActions', { // templates/deployments/global-actions.hbs
        outlet: 'globalActions',
        into: 'application'
    });
  },

  /**
   * prepares data for route which will be passed to the controller
   *
   * @method model
   * @param  {Object} params, handled by Embers routing
   * @return {Promise|Object} Object containing the systemId, revision, mode (deployments/architectures) and the instances
   * @public
   */
  model(params) {
    const systemId = params.systemId;
    this.set('systemId', systemId);

    [
        'node',
        'serviceinstance',
        'service',
        'communication',
        'communicationinstance',
        'nodegroup'
    ].forEach((modelName) => this.store.unloadAll(modelName)); // fresh start to avoid "outdated" from cached instances

    return this.loadSystem(systemId)
        .then(this.loadRevision.bind(this))
        .then((revision) => {
            return RSVP.resolve()
                .then(this.loadMetaModel.bind(this))
                .then(models => this.verifyRevision(revision, models))
                .then((models) => {
                    this.debug('loaded models', models);
                    return {
                        systemId: systemId,
                        revision: revision,
                        instances: models,
                        mode: this.get('routeName').split('.')[0] // deployments/architectures
                    };
                });
        })
        .catch(err => {
            console.error('could not load models. Error:', err);
            throw err; // TODO: handle errors in UI
        });
  },
  /**
   * load the current system record
   * @param  {String} systemId id of the system (from URL)
   * @return {Promise|SystemRecord}
   * @public
   */
  loadSystem(systemId) {
    return this.store.findRecord('system', systemId) // cached if user comes via navigation
        .then((system) => {
            this.debug('loaded system', system);
            this.set('session.system', system); // add the system to all requests
            const changelogStream = this.get('changelogStream'); // lazy loaded, requires session id
            changelogStream.connect(system.id);
            return system;
        });
  },
  /**
   * Loads the current revision from API (without caching)
   * @param  {SystemRecord}
   * @return {Object} API response, containing changelogSequence, revisionNumber and the lastUpdated date
   * @public
   */
  loadRevision(system) {
    return system.getRevision();
  },

  /**
   * note that findAll returns an Observable Array which automatically
   * update whenever new records are pushed into the store.
   * The controller can observe this.
   * Also note that since we changed the behavior of findAll() to use the systemId
   * Ember will probably also update for other systems. These are filtered in the controller.
   *
   * We also load all the data, so that the transformation strategies can assume that the whole
   * meta model is cached. This also allowes that the architecture view is only an alias
   *
   * @method loadMetaModel
   * @return {Promise|Object} Promise for an object containing the model records in lists, where the keys are the pluralized model names
   */
  loadMetaModel() {
    const load = (type) => this.store.findAll(type);
    return RSVP.hash({
        nodes: load('node'),
        nodeGroups: load('nodegroup'),
        services: load('service'),
        serviceInstances: load('serviceinstance'),
        communications: load('communication'),
        communicationInstances: load('communicationinstance')
    });
  },

  /**
   * checks whether any of the loaded instances from the API where created while the requests still in progress.
   *
   * @method verifyRevision
   * @param  {Object} revision Revision Object directly from the API (no Ember record, but plain Object)
   * @param  {Object} models   Object with all instances of a model, keys are the pluralized identiefers
   * @return {Promise|Object} promise that is rejected with 'outdated' if any instance is newer than expected, otherwise resolves models
   */
  verifyRevision(revision, models) {
    const outdatedRecords = Object
        .keys(models)
        .map(key => models[key])
        .filter(instances => {
            return instances.filter(record =>
                record.get('revisionNumber') >= revision.revisionNumber && record.get('changelogSequence') > revision.changelogSequence
            ).length > 0;
        });
    if(outdatedRecords.length > 0) {
        this.debug('records loaded from server seem to have changed during loading, refreshing data!', revision, models);
        return RSVP.Promise.reject('outdated');
    }

    return models;
  },
  actions: {
    error: function(err) {
       // debugger
        if(err === 'outdated') {
            // wait a bit to avoid DDOS, TODO: exponential backoff?
            this.debug('data is outdated, refreshing data');
            setTimeout(this.refresh.bind(this), 100); // refresh asynchronously to avoid infinite recursions
            return; // return otherwise transitions to this site will fail and it will go back to the home page.
        }
        console.error('unhandled error in deployments/single route', err);
        this.transitionTo('deployments');
        this.get('flashMessages').danger(`Could not find system with id "${this.get('systemId')}"`);
    },
    loadDetails(rawEntity) {
        this.debug('loadDetails action', rawEntity);
        const entityType = rawEntity.type.toLowerCase();
        const entityId = rawEntity.id;

        /* I would love to not generate the url first, but there seem to be unknown (to me) assumptions about
        * passing object parameters to transitionTo which break with the current path variables.
        * Otherwise this would use transitionTo('deployments.single.details', {...})
        */
        const url = this.router.generate(`${this.get('routeName')}.details`, { // use routeName to support architectures alias
            systemId: this.get('session.systemId'),
            entityType,
            entityId
        });
        this.replaceWith(url);
    },
    backToSystem() {
        this.replaceWith(this.get('routeName'));
    },
    willTransition(transition) {
        const currentRoute = this.get('routeName');
        const allowedRoutes = [ // support route aliases
            currentRoute.replace(/^deployments/, 'architectures'),
            currentRoute.replace(/^architectures/, 'deployments')
        ];
        const target = transition.targetName;
        const keepSocketConnection = target.indexOf(allowedRoutes[0]) === 0 || target.indexOf(allowedRoutes[1]) === 0;

        this.debug('transition', transition.targetName, currentRoute, allowedRoutes);

        // do not disconnect if transitioning to a child route (details) or alias
        if (!keepSocketConnection) {
            this.debug('disconnecting from changelogStream: leaving .single route');
            this.get('changelogStream').disconnect();
        } else {
            this.debug('keeping changelogStream connection open: navigating to subpage or alias');
        }
    }
  }
});
