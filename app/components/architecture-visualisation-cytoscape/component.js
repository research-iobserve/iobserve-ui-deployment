import Ember from 'ember';
import cytoscape from 'npm:cytoscape';
import cycola from 'npm:cytoscape-cola';
import cytoscapeStyle from './style';
// import cola from 'npm:webcola';
import _ from 'npm:lodash';
import coseBilkent from 'npm:cytoscape-cose-bilkent';

coseBilkent(cytoscape); // register

export default Ember.Component.extend({
    theme: null,
    layoutAlgorithm: 'cose',
    classNames: ['cytoscapeRenderingSpace'],
    init: function() {
        cycola( cytoscape, window.cola );
        this._super();
        this.debug('loaded', this.get('graph'));
    },
    willDestroyElement() {
        clearInterval(this.interval);
    },
    layoutChanged: function(newLayout) {
        this.debug('layout changed!', newLayout);
    }.observes('layout'),
    renderGraph: function() {
        this.debug('graph',this.get('theme'), this.get('graph'));
        this.cytoscape = cytoscape({
          container: this.element,

          boxSelectionEnabled: false,
          autounselectify: true,

          style: cytoscapeStyle(this.get('theme')),

          elements: _.cloneDeep(this.get('graph')), // TODO!

          layout: {
            name: this.get('layoutAlgorithm'),
            maxSimulationTime: 1000,
            padding: 6,
            ungrabifyWhileSimulating: true,
            infinite: false
          }
        });

        window.cy = cytoscape;
        window.cytoscape = this.cytoscape;
    }.on('didInsertElement').observes('layoutAlgorithm', 'graph', 'theme')
});
