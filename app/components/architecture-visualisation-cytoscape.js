import Ember from 'ember';
import cytoscape from 'npm:cytoscape';
import cycola from 'npm:cytoscape-cola';
// import cola from 'npm:webcola';
import _ from 'npm:lodash';

export default Ember.Component.extend({
    classNames: ['cytoscapeRenderingSpace'],
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

          style: [
            {
              selector: 'node',
              css: {
                'content': 'data(id)',
                'text-valign': 'center',
                'text-halign': 'center'
              }
            },
            {
              selector: '$node > node',
              css: {
                'padding-top': '10px',
                'padding-left': '10px',
                'padding-bottom': '10px',
                'padding-right': '10px',
                'text-valign': 'top',
                'text-halign': 'center',
                'background-color': '#bbb'
              }
            },
            {
              selector: 'edge',
              css: {
                'target-arrow-shape': 'triangle'
              }
            },
            {
              selector: ':selected',
              css: {
                'background-color': 'black',
                'line-color': 'black',
                'target-arrow-color': 'black',
                'source-arrow-color': 'black'
              }
            }
          ],

          elements: this.get('graph'),

          layout: {
            name: 'cola',
            padding: 5
          }
        });
    }
});


