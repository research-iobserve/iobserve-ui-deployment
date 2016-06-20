import Ember from 'ember';
import cytoscape from 'npm:cytoscape';
import cycola from 'npm:cytoscape-cola';
import cytoscapeStyle from './style';
// import cola from 'npm:webcola';
import _ from 'npm:lodash';

export default Ember.Component.extend({
    classNames: ['cytoscapeRenderingSpace'],
    dummyGraph: {
        nodes: [
            { data: { id: 'nodeWebnode', label: 'Webnode', type: 'node' } },
            { data: { id: 'nodeLogicnode', label: 'Logicnode', type: 'node' } },
            { data: { id: 'nodeAdapter', label: 'Adapter', type: 'node' } },
            { data: { id: 'nodeDatabase', label: 'Database', type: 'node' } },
            { data: { id: 'serviceInstanceFrontEnd', label: 'Database', type: 'serviceInstance', parent: 'nodeWebnode' } },
            { data: { id: 'serviceInstanceCashDesk', label: 'CashDesk', type: 'serviceInstance', parent: 'nodeLogicnode' } },
            { data: { id: 'serviceInstanceInventory', label: 'Inventory', type: 'serviceInstance' }, parent: 'nodeLogicnode' },
            { data: { id: 'serviceInstanceData', label: 'Data', type: 'serviceInstance', parent: 'nodeLogicnode' } },
            { data: { id: 'serviceInstancePostgresSQL', label: 'PostgresSQL', type: 'serviceInstance', parent: 'nodeDatabase' } },
        ],
        edges: [
            { data: { id: 'ad', source: 'a', target: 'd', weight: 100 } },
        ]
    },
    init: function() {
        cycola( cytoscape, cola );

        this._super();
        const self = this;
        const log = self.debug.bind(self);
        log('loaded');
        // this.interval = setInterval(function() {
        //     const graph = self.get('graph');

        //     log('update interval called', graph);

        //     if(!graph) {
        //         return; // eventual consistent
        //     }
        //     const edges = graph.edges || [];
        //     const index = Math.floor(Math.random()*edges.length);
        //     if(Math.random() > 0.5) {
        //         edges.splice(index, 1);
        //     } else { // FIXME: does not render updates
        //         const children = _.get(graph, 'children', []);
        //         const randomNodeIndex = Math.floor(Math.random()*children.length);
        //         const randomNodeId = children[randomNodeIndex].id;
        //         graph.edges = _.set(edges, `${index}.target`, randomNodeId);
        //         log(`updating random edge (${index}) to target a random node id ${randomNodeId}`, randomNodeIndex, edges);
        //     }
        //     self.set('graph', graph);
        //     self.renderGraph();
        // }, 1000);
    },
    willDestroyElement() {
        clearInterval(this.interval);
    },
    didInsertElement: function() {
        this.cytoscape = cytoscape({
          container: this.element,

          boxSelectionEnabled: false,
          autounselectify: true,

          style: cytoscapeStyle,

          elements: this.get('graph'), // TODO!

          layout: {
            name: 'cola',
            padding: 5,
            maxSimulationTime: 1000
          }
        });
    }
});
