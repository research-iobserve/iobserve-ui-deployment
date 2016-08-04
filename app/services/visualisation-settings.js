import Ember from 'ember';
import Themes from '../utils/visualisation-themes';

const { Service, computed, assign } = Ember;

/**
 * Stores the settings for visualisations for a user.
 *
 * @class VisualisationSettingsService
 * @extends Ember.Service
 */
export default Service.extend({ // TODO: load and save to localstorage

    /**
     * the layout to use for the visualisation
     *
     * @property layoutAlgorithm
     * @type {String}
     */
    layoutAlgorithm: 'cose-bilkent',

    /**
     * list of supported layout algorithms
     *
     * @property layoutAlgorithms
     * @type {Array|String}
     * @readOnly
     */
    layoutAlgorithms: [
        'cose-bilkent',
        'cose',
        'cola',
        'grid',
        'concentric',
        'breadthfirst'
    ],

    /**
     * the theme to use for the visualisation
     *
     * @property theme
     * @type {String}
     */
    theme: 'iObserve',

    /**
     * Computed property that returns the CSS style object of the current theme.
     * Themes can override each property of the `iObserve` Theme
     *
     * @property themeStyle
     * @type {Object | CytoscapeCSS}
     * @readOnly
     */
    themeStyle: computed('theme', function() {
        return assign({}, Themes.iObserve, Themes[this.get('theme')]);
    }),
    /**
     * List of available themes.
     *
     * @property themes
     * @type {Array|String}
     * @readOnly
     */
    themes: Object.keys(Themes)
});