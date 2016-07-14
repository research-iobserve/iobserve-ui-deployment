import Ember from 'ember';

const { Component, computed, observer } = Ember;

export default Component.extend({
    timeSeries: null,
    currentTab: 0,
    isCurrentTab: observer('currentTab', function(index) {
        return this.get('currentTab') === index;
    }),
    currentSeries: computed('timeSeries', 'currentTab', function() {
        const allSeries = this.get(`timeSeries`);
        return allSeries && allSeries[this.get('currentTab')];
    }),
    actions: {
        clickTab(index){
            this.set('currentTab', index);
        }
    }
});