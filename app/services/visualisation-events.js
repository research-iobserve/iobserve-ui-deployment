import Ember from 'ember';

/**
 * This service allows to bind global events which influence a visualisation.
 * Since cytoscape visualisations are not behaving like regular DOM elements,
 * the service provides means let the visualisation know about environment changes.
 * As of time of writing it only provides events for resizing
 *
 * @class VisualisationEventsService
 * @extends {Ember.Service}
 */
export default Ember.Service.extend({
    /**
     * Property that signlas if the visualisation is currently expanding/contracting.
     * Use observers to subscribe to changes
     *
     * @type {Boolean}
     */
    isResizing: false
});