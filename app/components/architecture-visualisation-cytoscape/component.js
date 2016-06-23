import Ember from 'ember';
import cytoscape from 'npm:cytoscape';
import cycola from 'npm:cytoscape-cola';
import cytoscapeStyle from './style';
// import cola from 'npm:webcola';
import _ from 'npm:lodash';
import coseBilkent from 'npm:cytoscape-cose-bilkent';

coseBilkent(cytoscape); // register

export default Ember.Component.extend({
    theme: null, // set by architecture-viewer
    layoutAlgorithm: null, // set by architecture-viewer
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
        this.rendering = cytoscape({
          container: this.element,

          boxSelectionEnabled: false,
          autounselectify: true,

          style: cytoscapeStyle(this.get('theme')),

          elements: _.cloneDeep(this.get('graph')),

          layout: {
            name: this.get('layoutAlgorithm'),
            // maxSimulationTime: 1000,
            // padding: 6,
            // ungrabifyWhileSimulating: true,
            // infinite: false
            // TODO: avoidOverlap: true has shaky behavior (enabled by default). Find workaround
          }
        });

        this.rendering.on('click', (event) => {
            const target = event.cyTarget;
            const data = target && target.data && target.data();
            if(data && data.id) {
                this.debug('clicked on element in graph', data, event);
                const action = this.get('select');
                if(action) {
                    action(data);
                } else {
                    this.debug('select action not set, ignoring click');
                }
            } else {
                this.debug('clicked on non-selectable entity', event);
            }

        });

        // just for development purposes - TODO: remove
        window.cy = cytoscape;
        window.cytoscape = this.rendering;
    }.on('didInsertElement').observes('layoutAlgorithm', 'graph', 'theme')
});
