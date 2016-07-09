import Ember from 'ember';

/**
 * parses a list of models and creates stores them
 */
export default Ember.Service.extend({
  createGraph(models) {
    this.debug('loaded models', models);
    const serviceInstances = models.serviceInstances;
    // const services = models.services; // not used in current view
    const communicationInstances = models.communicationInstances;
    const nodeGroups = models.nodeGroups;
    const nodes = models.nodes;

    var network = {
      nodes: [],
      edges: []
    };

    nodeGroups.forEach(instance => {
        const data = instance.serialize();
        data.label = data.name;

      network.nodes.push({
        data: data
    });});

    nodes.forEach(instance => {
        const data = instance.serialize();
        data.label = data.name;
        data.parent = data.nodeGroupId;

      network.nodes.push({
        data: data
    });});

    serviceInstances.forEach(instance => {
        const data = instance.serialize();
        data.label = data.name;
        data.parent = data.nodeId;

      network.nodes.push({
        data: data
    });});

    communicationInstances.forEach(instance => {
        const data = instance.serialize();
        data.source = data.sourceId;
        data.target = data.targetId;
        data.technology = instance.store.peekRecord('communication', data.communicationId).get('technology');
        data.label = data.technology;

        network.edges.push({ data });
    });

    return network;
  }
});
