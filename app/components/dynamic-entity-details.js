import Ember from 'ember';

const { Component, computed, observer, on } = Ember;

export default Component.extend({
    entity: null,
    currentSeriesTab: 0,
    currentMode: 'series',
    init() {
        if(!this.get('entity.statusInformations')) {
            this.set('currentMode', 'series');
        }
        setInterval(() => this.debug('statusInformations', this.get('entity.statusInformations')), 1000);
        this._super(...arguments);
    },
    updateDefaultTab: observer('entity.statusInformations', function() {
        if(this.get('entity.statusInformations')) { // will load asynchronously (findRecord)
            this.set('currentMode', 'statusInformations');
        }
    }),
    isCurrentSeriesTab: observer('currentSeriesTab', 'currentMode', function(index) {
        return this.get('currentMode') === 'series' && this.get('currentSeriesTab') === index;
    }),
    currentSeries: computed('entity.timeSeries', 'currentSeriesTab', 'currentMode', function() {
        if(this.get('currentMode') !== 'series') {
            return false;
        }
        const allSeries = this.get('entity.timeSeries');
        return allSeries && allSeries[this.get('currentSeriesTab')];
    }),
    statusInformationsSelected: computed('currentMode', function() {
        return this.get('currentMode') === 'statusInformations';
    }),
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