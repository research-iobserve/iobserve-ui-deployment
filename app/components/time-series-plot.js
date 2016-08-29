import Ember from 'ember';
// requires flot.js to be included in vendor.js (via ember-cli-build.js)

const { Component, on, observer, computed, get} = Ember;

export default Component.extend({
    visualisationEvents: Ember.inject.service(),
    tagName: 'div',
    attributeBindings: ['style'],
    timeSeries: [],
    options: {
        xaxis: {
            mode: 'time'
            // timezone: 'browser' // TODO: from Server?
        },
        yaxis: {
        }
    },
    height: 300,
    plot: null,
    init() {
        this._super(...arguments);
    },
    style: computed('height', function () { // flot requires a fix height
        return Ember.String.htmlSafe(`height: ${this.get('height')}px;`);
    }),
    resize: observer('visualisationEvents.resizing', function() {
        const plot = this.get('plot');
        const isResizing = this.get('visualisationEvents.resizing');
        if(plot && !isResizing) { // trigger after resize is done
            plot.resize();
            plot.setupGrid();
            plot.draw();
        }
    }),
    willDestroy() {
        this.set('plot', null);
        this._super(...arguments);
    },
    // also observe visualisation settings as resizing will apply display:none, leading to flot not rendering since it requires a width
    renderPlot: on('didRender', observer('timeSeries.[]', 'visualisationEvents.resizing', function () {
        const isResizing = this.get('visualisationEvents.resizing');
        if(!this.element || isResizing) {
            return;
        }
        this.debug('rendering plot');
        const $this = this.$();
        // flot data points are tuples/arrays [x,y], graphs are arrays of these
        // use Ember.get because it would work with Ember.Object and plain JS
        const plotData = this.get('timeSeries.series')
            .map((valueObj) => [get(valueObj, 'timestamp'), get(valueObj, 'value')]);

        // this.set('options.yaxis.axisLabel', this.get('timeSeries.valueLabel'));

        // wrap in additional array since flot can handle multiple graphs at once, we only need one
        try{
            const plot = $this.plot([plotData], this.get('options')).data('plot');
            this.set('plot', plot);
        } catch(e) {
            console.error('flot setup encountered an issue', e);
        }
    }))
});