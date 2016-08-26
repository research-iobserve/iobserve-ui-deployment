import Ember from 'ember';

const { Component, inject, computed } = Ember;

export default Component.extend({
    visualisationEvents: inject.service(),
    graph: null,
    entityDetails: null,
    classNameBindings: ['resizing'], // triggers deprecation warning for visualisationEvents.resizing from SingleDeploymentDetails.endAnimation
    resizing: computed('visualisationEvents.resizing', function() {
        return this.get('visualisationEvents.resizing');
    }),
    init() {
        this._super();
        this.debug('init!', this.get('graph'), this.get('layoutAlgorithm'));
    },
    actions: {
        graphClick(rawEntity) {
            this.set('entityDetails', rawEntity);
        }
    }
});
