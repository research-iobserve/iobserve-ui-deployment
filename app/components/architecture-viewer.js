import Ember from 'ember';
import Themes from './architecture-visualisation-cytoscape/themes';

export default Ember.Component.extend({
    graph: null,
    entityDetails: null,
    layoutAlgorithm: 'cose',
    theme: Themes[Object.keys(Themes)[0]], // first theme
    themes: Object.keys(Themes),
    layoutAlgorithms: [
        'cose',
        //'cose-bilkent', // broken - see https://github.com/cytoscape/cytoscape.js-cose-bilkent/issues/18
        'cola',
        'grid',
        'concentric',
        'breadthfirst'
    ],
    init() {
        this._super();
        this.debug('init!', this.get('graph'), this.get('layoutAlgorithm'));
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
