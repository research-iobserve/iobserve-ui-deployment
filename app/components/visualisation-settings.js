import Ember from 'ember';

const { Component, inject } = Ember;

/**
 * Dropdown menu for setting options for the visualisation settings
 *
 * @class VisualisationSettingsComponent
 * @extends Ember.Component
 * @uses VisualisationSettingsService
 */
export default Component.extend({
    visualisationSettings: inject.service(),
    classNames: ['dropdown'],
    tagName: 'div',
    actions: {
        selectLayoutAlgorithm(value) {
            this.debug('value', value);
            this.set('visualisationSettings.layoutAlgorithm', value);
        },
        selectTheme(theme) {
            this.set('visualisationSettings.theme', theme);
        }
    }
});
