import Ember from 'ember';
import cytoscape from 'npm:cytoscape';
import cycola from 'npm:cytoscape-cola';
import cytoscapeStyle from './style';
// import cola from 'npm:webcola';
import _ from 'npm:lodash';

export default Ember.Component.extend({
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
    layoutChanged: Ember.observer('layout', function(newLayout) {
        this.debug('layout changed!', newLayout);
    }),
    renderGraph: function() {
        this.debug('graph', this.get('graph'));
        this.cytoscape = cytoscape({
          container: this.element,

          boxSelectionEnabled: false,
          autounselectify: true,

          style: cytoscapeStyle({
                  nodeGroupTextColor: '#3399CC',
                  nodeGroupColor: 'white',
                  borderColor: '#39588A',

                  nodeColor: '#B4DCED',
                  nodeTextColor: '#3399CC',

                  serviceColor: '#E8F8FF',
                  serviceTextColor: '#3399CC',

                  arrowBorderColor: '#3399CC',
                  arrowColor: '#3399CC',
                  arrowLabelColor: 'black'
              }
          ),

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
    }.on('didInsertElement').observes('layoutAlgorithm', 'graph')
});
