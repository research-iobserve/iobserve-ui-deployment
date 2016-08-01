import Ember from 'ember';
import Themes from '../components/architecture-visualisation-cytoscape/themes';

const { Service } = Ember;

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
    theme: Themes[Object.keys(Themes)[0]], // first theme // TODO use a "default: true" flag, order is not fixed for browsers

    /**
     * List of available themes.
     *
     * @property themes
     * @type {Array|String}
     * @readOnly
     */
    themes: Object.keys(Themes),
    setThemeByName(name) {
        this.set('theme', Themes[name]);
    }
});