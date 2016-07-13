import Ember from 'ember';
// requires flot.js to be included in vendor.js (via ember-cli-build.js)

const { Component, on, observer, computed, String, get} = Ember;

export default Component.extend({
    tagName: 'div',
    attributeBindings: ['style'],
    timeSeries: [],
    options: {},
    height: 300,
    plot: null,
    style: computed('height', function () { // flot requires a fix height
        return String.htmlSafe(`height: ${this.get('height')}px;`);
    }),
    renderPlot: on('didInsertElement', observer('timeSeries.[]', function () {
        this.debug('rendering plot');
        if(!this.element) {
            return;
        }
        const $this = this.$();
        // flot data points are tuples/arrays [x,y], graphs are arrays of these
        // TODO: index is not enough, should show timestamp or other x-axis label
        const plotData = this.get('timeSeries.series').map((valueObj, index) => [index, get(valueObj, 'value')]);
        // wrap in additional array since flot can handle multiple graphs at once, we only need one
        const plot = $this.plot([plotData], this.get('options')).data('plot');
        this.set('plot', plot);
    }))
});