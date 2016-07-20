import Ember from 'ember';

/**
 * parses a list of models and creates stores them
 */
export default Ember.Service.extend({
  store: Ember.inject.service(),
  createGraph(models) {
    this.debug('loaded models', models);

    // prepare models by serializing them
    const prepared = {};
    [   'services',
        'serviceInstances',
        'communications'
    ].forEach((key) => {
        const records = models[key];
        prepared[key] = records.map(record => record.serialize());
    });

    // services not used in current view
    const {services, serviceInstances, communications} = prepared;

    var network = {
      nodes: [],
      edges: []
    };

    services.forEach(data => {
        data.label = data.name;
        network.nodes.push({
            data: data
        });
    });

    // serviceInstances.forEach(data => {
    //     data.label = data.name;
    //     data.parent = data.serviceId;
    //
    //     network.nodes.push({
    //         data: data,
    //         classes: data.status || ''
    //     });
    // });

    communications.forEach(data => {
        data.source = data.sourceId;
        data.target = data.targetId;
        data.label = data.technology;

        const instanceList = this.get('store').peekAll('communicationinstance')
            .filter((instance) => instance.get('communicationId') === data.id );

        data.workload = instanceList.reduce((previous, instance) => previous + instance.get('workload'), 0);

        //TODO Normalize

        network.edges.push({
            data,
            classes: data.status || ''
        });
    });

    return network;
  }
});
