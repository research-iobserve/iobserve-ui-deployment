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
        ]).then(createGraph).finally((graph) => {
            this.set('loading', false);
            return graph;
        });
    }
});
