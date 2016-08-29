import Ember from 'ember';

const { Service, computed, Evented } = Ember;

/**
 * @event resizeStart
 */
const START_EVENT = 'resizeStart';
/**
 * @event resizeEnd
 */
const END_EVENT = 'resizeStart';
/**
 * This service allows to bind global events which influence a visualisation.
 * Since cytoscape visualisations are not behaving like regular DOM elements,
 * the service provides means let the visualisation know about environment changes.
 * As of time of writing it only provides events for resizing.
 *
 * @class VisualisationEventsService
 * @extends {Ember.Service}
 * @uses Ember.Evented
 */
export default Service.extend(Evented, {
    /**
     * whether the resizing animation takes place
     *
     * @property _isResizing
     * @type {Boolean}
     * @private
     */
    _isResizing: false,
    /**
     * Property that signals if the visualisation is currently expanding/contracting.
     * Use observers to subscribe to changes
     *
     * @property isResizing
     * @type {Boolean}
     * @readonly
     */
    isResizing: computed('_isResizing', function() {
        return this.get('_isResizing');
    }),

    /**
     * set state to resizing and notify observers
     *
     * @method startResizing
     */
    startResizing() {
        this.set('_isResizing', true);
        this.debug('resizing started!');
        this.trigger(START_EVENT);
    },

    /**
     * set state to not resizing and notify observers
     *
     * @method endResizing
     */
    endResizing() {
        this.set('_isResizing', false);
        this.debug('resizing ended!');
        this.trigger(END_EVENT);
    }
});