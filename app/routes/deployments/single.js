import Ember from 'ember';

export default Ember.Route.extend({
  session: Ember.inject.service(), // loads services/session.js
  graphingService: Ember.inject.service(),
  changelogStream: Ember.inject.service(),
  model(params) {
    const systemId = params.systemId;
    this.set('session.systemId', systemId); // add the system to all requests
    const changelogStream = this.get('changelogStream'); // lazy loaded, requires session id
    changelogStream.connect(systemId);

    const graphingService = this.get('graphingService');
    const createGraph = graphingService.createGraph.bind(graphingService);

    this.set('loading', true);
    return Ember.RSVP.hash({
        nodes: this.store.findAll('node'),
        nodeGroups: this.store.findAll('nodegroup'),
        services: this.store.findAll('service'),
        serviceInstances: this.store.findAll('serviceinstance'),
        communications: this.store.findAll('communication'),
        communicationInstances: this.store.findAll('communicationinstance')
    }).then(createGraph).then((graph) => {
      this.set('loading', false);
      return graph;
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
    willTransition() {
        this.get('changelogStream').disconnect();
    }
  }
});
