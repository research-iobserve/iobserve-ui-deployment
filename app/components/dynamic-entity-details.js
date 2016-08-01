import Ember from 'ember';

const { Component, computed, observer /*, on */ } = Ember;
/**
 * Displays tabs which contain the dynamic information nested within any `Measurable` entity, `statusInformations` and `timeSeries`.
 * Will also handle the case where there is no information available.
 *
 * @class DynamicEntityDetailsComponent
 * @extends Ember.Component
 * @uses KeyValueTableComponent
 * @uses EntityDetailsComponent
 */
export default Component.extend({
    /**
     * the model instance for the entity which can contain the `timeSeries` and `statusInformations` properties
     * @type {Measurable}
     * @property entity
     * @required
     * @public
     */
    entity: null,

    /**
     * stores which time series tab is active (if `mode == series`)
     * @type {Number}
     * @private
     */
    currentSeriesTab: 0,

    /**
     * store the mode, either `series` or `statusInformations'
     * @type {String}
     * @default series
     * @private
     */
    currentMode: 'series',
    init() {
        const statusInfo = this.get('entity.statusInformations');
        if(statusInfo && statusInfo.length > 0) {
            this.set('currentMode', 'statusInformations');
        } else {
            this.set('currentMode', 'series');
        }
        this._super(...arguments);
    },
    /**
     * observer that gets called whenever the `statusInformations` or entity changes.
     * Resets the current clicked tab so that the default open tab always contains visible data.
     *
     * @method updateDefaultTab
     * @private
     */
    updateDefaultTab: observer('entity.statusInformations', 'entity.timeSeries', function() {
        const statusInfo = this.get('entity.statusInformations');
        if(statusInfo && statusInfo.length > 0) { // will load asynchronously (findRecord)
            this.set('currentMode', 'statusInformations');
        } else {
            this.set('currentMode', 'series');
        }
        this.set('currentSeriesTab', 0);
    }),

    /**
     * Computed property which returns the currently selected series element, if open.
     *
     * @property currentSeries
     * @return {TimeSeries} `undefined` if no series selected
     * @private
     * @readOnly
     */
    currentSeries: computed('entity.timeSeries', 'currentSeriesTab', 'currentMode', function() {
        if(this.get('currentMode') !== 'series') {
            return false;
        }
        const allSeries = this.get('entity.timeSeries');
        return allSeries && allSeries[this.get('currentSeriesTab')];
    }),

    /**
     * Computed property which returns whether or not the currently open tab is statusInformations
     *
     * @property statusInformationsSelected
     * @return {Boolean} is open
     * @private
     * @readOnly
     */
    statusInformationsSelected: computed('currentMode', function() {
        return this.get('currentMode') === 'statusInformations';
    }),

    /**
     * Computed property which return whether or not data is available (`timeSeries` or `statusInformations`)
     * @property isDataAvailable
     * @return {Boolean}
     * @private
     * @readOnly
     */
    isDataAvailable: computed('entity.{statusInformations,timeSeries}', function() {
        return !!(this.get('currentSeries') || this.get('statusInformationsSelected'));
    }),
    actions: {
        clickSeriesTab(index){
            this.set('currentMode', 'series');
            this.set('currentSeriesTab', index);
        },
        clickStatusInfosTab(){
            this.set('currentMode', 'statusInformations');
        }
    }
});