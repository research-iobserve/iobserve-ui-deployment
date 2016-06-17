import Ember from 'ember';

export default Ember.Route.extend({
  session: Ember.inject.service(), // loads services/session.js
  graphingService: Ember.inject.service(),
  model(params) {
    this.set('session.systemId', params.systemId); // add the system to all requests
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
  }
});
