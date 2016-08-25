import Ember from 'ember';
import cytoscape from 'npm:cytoscape';
import cycola from 'npm:cytoscape-cola';
import cytoscapeStyle from './style';
import _ from 'npm:lodash';
import coseBilkent from 'npm:cytoscape-cose-bilkent';

const { Component, inject, observer, on } = Ember;

coseBilkent(cytoscape); // register

/**
 * renders a cytoscape compatible graph structure in a canvas
 *
 * @class CytoscapeVisualisationComponent
 * @extends Ember.Component
 * @uses Cytoscape
 */
export default Component.extend({
    visualisationEvents: inject.service(),
    visualisationSettings: inject.service(),
    classNames: ['cytoscapeRenderingSpace'],
    _clickedElement: null,
    init: function() {
        cycola( cytoscape, window.cola );
        this._super();
        this.debug('loaded', this.get('graph'));

        // we only need to listen to :end since opacity changes are done via css, since architecture-viewer adds a resizing class
        const visualisationEvents = this.get('visualisationEvents');
        const resizeListener = this.resize.bind(this);
        visualisationEvents.on('resize:end', resizeListener);
        this.on('willDestroyElement', () => {
            visualisationEvents.off('resize:start', resizeListener);
        });
    },
    /**
     * Observer method that renders the visualisation in a canvas using Cytoscape
     * @method renderGraph
     */
    renderGraph: on('didInsertElement', observer('visualisationSettings.{layoutAlgorithm,theme}', 'graph', function() {
        this.debug('graph', this.get('visualisationSettings.theme'), this.get('visualisationSettings.layoutAlgorithm'), this.get('graph'));
        this.rendering = cytoscape({
          container: this.element,

          boxSelectionEnabled: false,
          autounselectify: true,

          style: cytoscapeStyle(this.get('visualisationSettings.themeStyle')),

          elements: _.cloneDeep(this.get('graph')),

          layout: {
            name: this.get('visualisationSettings.layoutAlgorithm'),
            randomize: false // kose-bilkent will randomize node positions
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
            this.set('_clickedElement', target);
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
    })),
    resize() {
        if(this.rendering) {
            this.rendering.resize();
            this.rendering.center(this.get('_clickedElement')); // TODO: keep focus or even focus clicked element
        }
    }
});
