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
    return Ember.RSVP.Promise.all([
      this.store.findAll('node'),
      this.store.findAll('nodegroup'),
      this.store.findAll('service'),
      this.store.findAll('serviceinstance'),
      this.store.findAll('communication'),
      this.store.findAll('communicationinstance')
  ]).then(responses => {
      return {
        nodes: responses.get(0),
        nodeGroups: responses.get(1),
        services: responses.get(2),
        serviceInstances: responses.get(3),
        communications: responses.get(4),
        communicationInstances: responses.get(5)
    };}).then(createGraph).then((graph) => {
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
