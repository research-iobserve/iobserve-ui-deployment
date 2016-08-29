import Ember from 'ember';
// requires flot.js to be included in vendor.js (via ember-cli-build.js)

const { Component, on, observer, computed, get, inject} = Ember;

// FIXME docs!
export default Component.extend({
    visualisationEvents: inject.service(),
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
        const visualisationEvents = this.get('visualisationEvents');

        const resizeListener = this.resize.bind(this);
        visualisationEvents.on('resizeEnd', resizeListener);
        this.on('willDestroyElement', function() {
            visualisationEvents.off('resizeEnd', resizeListener);
        });
    },
    style: computed('height', function () { // flot requires a fix height
        return Ember.String.htmlSafe(`height: ${this.get('height')}px;`);
    }),
    resize: function() {

        const plot = this.get('plot');
        if(plot) { // trigger after resize is done
            this.debug('resizing plot');
            plot.resize();
            plot.setupGrid();
            plot.draw();
        } else if(!plot) {
            this.renderPlot();
        }
    },
    willDestroy() {
        this.set('plot', null);
        this._super(...arguments);
    },
    // also observe visualisation settings as resizing will apply display:none, leading to flot not rendering since it requires a width
    renderPlot: on('didRender', observer('timeSeries.[]', function () {
        const isResizing = this.get('visualisationEvents.isResizing');
        if(!this.element || isResizing) {
            return;
        }
        this.debug('rendering plot');
        const $this = this.$();
        // flot data points are tuples/arrays [x,y], graphs are arrays of these
        // use Ember.get because it would work with Ember.Object and plain JS
        const plotData = this.get('timeSeries.series')
            .map((valueObj) => [get(valueObj, 'timestamp'), get(valueObj, 'value')]);

        this.set('options.yaxis.axisLabel', this.get('timeSeries.valueLabel'));

        // wrap in additional array since flot can handle multiple graphs at once, we only need one
        try{
            const plot = $this.plot([plotData], this.get('options')).data('plot');
            this.set('plot', plot);
        } catch(e) {
            console.error('flot setup encountered an issue', e);
        }
    }))
});