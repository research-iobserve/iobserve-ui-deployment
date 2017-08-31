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
    'communications',
    'usergroups'
    ].forEach((key) => {
        const records = models[key];
        prepared[key] = records.map(record => record.serialize());
    });

    // services not used in current view
    const {services, /* serviceInstances,*/ communications, usergroups} = prepared;

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

        //width of communication lines normalized 
        const min_width = 1;
        const max_width = 16;
        var min_calls = Number.MAX_SAFE_INTEGER;
        var max_calls = 0;

        this.get('store').peekAll('communicationinstance').forEach((instance) => {
            
            if (instance.get('workload') > max_calls){
                max_calls = instance.get('workload');
            }

            if (instance.get('workload') < min_calls){
                min_calls = instance.get('workload');
            }
        });

        data.workload = instanceList.reduce((previous, instance) => previous + instance.get('workload'), 0);
        
        if (min_calls === max_calls){
            data.workload = max_width;        
        } else {
            const a = (max_width - min_width)/(Math.log(max_calls)- Math.log(min_calls));
            const b = max_width - a * Math.log(max_calls);

            data.workload = a * Math.log(data.workload) + b;
        }

        
        
        network.edges.push({
            data,
            classes: data.status || ''
        });
    });

//nodes for usergroups
usergroups.forEach(data => {
    data.label = data.name;
    network.nodes.push({
        data: data
    });
});

    //connection for usergroups
    usergroups.forEach(data => {
        const calledServicesList = data.calledServices;
        calledServicesList.forEach(service =>{
            //source: usergroupPerson, target: service
            console.log('target:', service);
            
        });

    });

    return network;
}
});
