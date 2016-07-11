import Ember from 'ember';
import Themes from './architecture-visualisation-cytoscape/themes';

export default Ember.Component.extend({
    visualisationEvents: Ember.inject.service(),
    graph: null,
    entityDetails: null,
    layoutAlgorithm: 'cose-bilkent',
    theme: Themes[Object.keys(Themes)[0]], // first theme // TODO use a "default: true" flag, order is not fixed for browsers
    themes: Object.keys(Themes),
    classNameBindings: ['resizing'],
    layoutAlgorithms: [
        'cose-bilkent',
        'cose',
        'cola',
        'grid',
        'concentric',
        'breadthfirst'
    ],
    init() {
        this._super();
        this.debug('init!', this.get('graph'), this.get('layoutAlgorithm'));

        // add class while the sidebar grows
        const visualisationEvents = this.get('visualisationEvents');
        visualisationEvents.on('resize:start', () => this.set('resizing', true));
        visualisationEvents.on('resize:end', () => this.set('resizing', false));
    },
    actions: {
        selectLayoutAlgorithm(value) {
            this.debug('value', value);
            this.set('layoutAlgorithm', value);
        },
        selectTheme(theme) {
            this.set('theme', Themes[theme]);
        },
        graphClick(rawEntity) {
            this.set('entityDetails', rawEntity);
        }
    }
});
