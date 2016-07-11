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
export default Ember.Service.extend(Ember.Evented, {
    /**
     * forces listing of allowed events to improve documentations.
     * Currently only resizing, used by deployments/single route (triggers events),
     * cytoscape and architecture viewer component.
     *
     * @type {Array}
     * @property allowedEvents
     * @default resize:start, resize:end
     */
    allowedEvents: [
        'resize:start',
        'resize:end'
    ],
    /**
     * triggers an events, see Ember docs.
     *
     * @method trigger
     * @parameter {String} eventName
     * @throws {Error} exception if an unknown event name was used.
     */
    trigger(eventName) {
        if(this.get('allowedEvents').indexOf(eventName) < 0) {
            throw new Error(`unknown event "${eventName}"`);
        } else {
            this._super(...arguments);
        }
    },
    /**
     * Subscribes to an event, see Ember docs.
     *
     * @method on
     * @parameter {String} eventName
     * @throws {Error} exception if an unknown event name was used.
     */
    on(eventName) {
        if(this.get('allowedEvents').indexOf(eventName) < 0) {
            throw new Error(`unknown event "${eventName}"`);
        } else {
            this._super(...arguments);
        }
    },
});