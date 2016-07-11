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
    resizing: false,
    init() {
        this._super();
        this.debug('init!', this.get('graph'), this.get('layoutAlgorithm'));

        // add class while the sidebar grows
        const visualisationEvents = this.get('visualisationEvents');
        const startListener = () => this.set('resizing', true);
        const endListener = () => this.set('resizing', false);
        visualisationEvents.on('resize:start', startListener);
        visualisationEvents.on('resize:end', endListener);

        this.on('willDestroyElement', () => {
            visualisationEvents.off('resize:start', startListener);
            visualisationEvents.off('resize:end', endListener);
        });
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
