import Ember from 'ember';

export default Ember.Route.extend({
  session: Ember.inject.service(), // loads services/session.js
  changelogStream: Ember.inject.service(),
  model(params) {
    const systemId = params.systemId;


    /*
     * note that findAll returns an Observable Array which automatically
     * update whenever new records are pushed into the store.
     * The controller can observe this.
     * Also note that since we changed the behavior of findAll() to use the systemId
     * Ember will probably also update for other systems. These are filtered in the controller.
     *
     * We also load all the data, so that the transformation strategies can assume that the whole
     * meta model is cached. This also allowes that the architecture view is only an alias
     */
    return this.loadSystem(systemId)
        .then(this.loadRevision.bind(this))
        .then((revision) => {
            return Ember.RSVP.resolve()
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
            if(err === 'outdated') {
                // wait a bit to avoid DDOS, TODO: exponential backoff?
                this.refresh();
            } else {
                console.error('could not load models', err);
            }
            throw err; // TODO: handle errors in UI
        });
  },
  loadSystem(systemId) {
    return this.store.findRecord('system', systemId)
        .then((system) => {
            this.debug('loaded system', system);
            this.set('session.system', system); // add the system to all requests
            const changelogStream = this.get('changelogStream'); // lazy loaded, requires session id
            changelogStream.connect(system.id);
            return system;
        });
  },
  loadRevision(system) {
    return system.getRevision();
  },
  loadMetaModel(system) {
    const load = (type) => this.store.findAll(type);
    return Ember.RSVP.hash({
        nodes: load('node'),
        nodeGroups: load('nodegroup'),
        services: load('service'),
        serviceInstances: load('serviceinstance'),
        communications: load('communication'),
        communicationInstances: load('communicationinstance')
    });
  },
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
        return Ember.RSVP.Promise.reject('outdated');
    }

    return models;
  },
  actions: {
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
        this.debug('transition', transition.targetName, this.get('routeName'));
        // do not disconnect if transitioning to a child route (details)
        if (transition.targetName.indexOf(this.get('routeName')) !== 0) {
            this.get('changelogStream').disconnect();
            clearTimeout(this.get('refreshTimeout'));
        }
    }
  }
});
