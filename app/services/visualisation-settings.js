import Ember from 'ember';
import Themes from '../utils/visualisation-themes';

const { Service, computed, assign, observer } = Ember;

/**
 * Stores the settings for visualisations for a user. Synchronizes with `localStorage` if possible
 *
 * @class VisualisationSettingsService
 * @extends Ember.Service
 */
export default Service.extend({

    /**
     * Initializes the service by loading the settings from `localStorage` (if possible)
     * @method init
     */
    init() {
        // load from local storage
        if(window.localStorage) {
            const settingsString = window.localStorage.getItem('visualisationSettings');
            // user might visit the first time
            if(settingsString) {
                const settings = JSON.parse(settingsString);
                // themes/layouts may be deleted
                if(Themes[settings.theme]) {
                    this.set('theme', settings.theme);
                }
                if(this.get('layoutAlgorithms').indexOf(settings.layoutAlgorithm) >= 0) {
                    this.set('layoutAlgorithm', settings.layoutAlgorithm);
                }
            }
        }

        this._super(...arguments);
    },

    /**
     * Observer of theme and layoutAlgorithm that stores the current settings to `localStorage`  (if possible)
     * @method saveToStorage
     * @private
     */
    saveToStorage: observer('theme', 'layoutAlgorithm', function() {
        if(window.localStorage) {
            const toSave = JSON.stringify({
                theme: this.get('theme'),
                layoutAlgorithm: this.get('layoutAlgorithm')
            });
            this.debug('saving settings to localStorage', toSave);
            window.localStorage.setItem('visualisationSettings', toSave);
        } else {
            this.debug('could not save to localStorage as it is not accessible');
        }
    }),

    /**
     * the layout to use for the visualisation
     *
     * @property layoutAlgorithm
     * @type {String}
     */
    layoutAlgorithm: 'cola',

    /**
     * list of supported layout algorithms
     *
     * @property layoutAlgorithms
     * @type {Array|String}
     * @readOnly
     */
    layoutAlgorithms: [
        'cola',
        'cose-bilkent',
        'cose',
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