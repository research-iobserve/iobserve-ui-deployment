import Ember from 'ember';

export default Ember.Route.extend({
  session: Ember.inject.service(), // loads services/session.js
  changelogStream: Ember.inject.service(),
  model(params) {
    const systemId = params.systemId;
    this.set('session.systemId', systemId); // add the system to all requests
    const changelogStream = this.get('changelogStream'); // lazy loaded, requires session id
    changelogStream.connect(systemId);

    /*
     * note that findAll returns an Observable Array which automatically
     * update whenever new records are pushed into the store.
     * The controller can observe this.
     * Also note that since we changed the behavior of findAll() to use the systemId
     * Ember will probably also update for other systems. These are filtered in the controller
     */
    const load = (type) => this.store.findAll(type);

    return Ember.RSVP.hash({
        nodes: load('node'),
        nodeGroups: load('nodegroup'),
        services: load('service'),
        serviceInstances: load('serviceinstance'),
        communications: load('communication'),
        communicationInstances: load('communicationinstance')
    }).then((models) => {
        this.debug('loaded models', models);
        return {
            systemId: systemId,
            instances: models
        };
    });
  },

  actions: {
    loadDetails(rawEntity) {
        this.debug('loadDetails action', rawEntity);
        const entityType = rawEntity.type.toLowerCase();
        const entityId = rawEntity.id;

        /* I would love to not generate the url first, but there seem to be unknown (to me) assumptions about
        * passing object parameters to transitionTo which break with the current path variables.
        */
        const url = this.router.generate('deployments.single.details', {
            systemId: this.get('session.systemId'),
            entityType,
            entityId
        });
        this.transitionTo(url);
    },
    willTransition(transition) {
        // do not disconnect if transitioning to a child route (details)
        if (transition.targetName.indexOf(this.get('routeName')) !== 0) {
            this.get('changelogStream').disconnect();
        }
    }
  }
});
