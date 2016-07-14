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
    [
        'serviceInstances',
        'communicationInstances',
        'nodeGroups',
        'nodes'
    ].forEach((key) => {
        const records = models[key];
        prepared[key] = records.map(record => record.serialize());
    });

    // services not used in current view
    const {serviceInstances, communicationInstances, nodeGroups, nodes} = prepared;

    var network = {
      nodes: [],
      edges: []
    };

    nodeGroups.forEach(data => {
        data.label = data.name;

        network.nodes.push({
            data: data
        });
    });

    nodes.forEach(data => {
        data.label = data.name;
        data.parent = data.nodeGroupId;

        network.nodes.push({
            data: data,
            classes: data.status || ''
        });
    });

    serviceInstances.forEach(data => {
        data.label = data.name;
        data.parent = data.nodeId;

        network.nodes.push({
            data: data,
            classes: data.status || ''
        });
    });

    communicationInstances.forEach(data => {
        data.source = data.sourceId;
        data.target = data.targetId;
        data.technology = this.get('store').peekRecord('communication', data.communicationId).get('technology');
        data.label = data.technology;

        network.edges.push({
            data,
            classes: data.status || ''
        });
    });

    return network;
  }
});
