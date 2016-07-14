import Ember from 'ember';
// requires flot.js to be included in vendor.js (via ember-cli-build.js)

const { Component, on, observer, computed, String, get} = Ember;

export default Component.extend({
    visualisationEvents: Ember.inject.service(),
    tagName: 'div',
    attributeBindings: ['style'],
    timeSeries: [],
    options: {
        minTickSize: [1, 'hour'],
        xaxis: {
            mode: 'time',
            // timezone: 'browser' // TODO: from Server?
        },
        yaxis: {
            label: 'test'
        }
    },
    height: 300,
    plot: null,
    init() {
        const visualisationEvents = this.get('visualisationEvents');
        const resizeListener = this.resize.bind(this);
        visualisationEvents.on('resize:end', resizeListener);
        this._super(...arguments);
    },
    style: computed('height', function () { // flot requires a fix height
        return String.htmlSafe(`height: ${this.get('height')}px;`);
    }),
    resize() {
        const plot = this.get('plot');
        if(plot) {
            plot.resize();
            plot.setupGrid();
            plot.draw();
        }
    },
    willDestroy() {
        this.set('plot', null);
        this._super(...arguments);
    },
    renderPlot: on('didInsertElement', observer('timeSeries.[]', function () {
        this.debug('rendering plot');
        if(!this.element) {
            return;
        }
        const $this = this.$();
        // flot data points are tuples/arrays [x,y], graphs are arrays of these
        // converts unix timestamps to javascript timestamps (*1000)
        // use Ember.get because it would work with Ember.Object and plain JS
        const plotData = this.get('timeSeries.series')
            .map((valueObj) => [get(valueObj, 'timestamp')*1000, get(valueObj, 'value')]);
        this.debug('plotData', plotData);
        // wrap in additional array since flot can handle multiple graphs at once, we only need one
        const plot = $this.plot([plotData], this.get('options')).data('plot');
        this.set('plot', plot);
    }))
});