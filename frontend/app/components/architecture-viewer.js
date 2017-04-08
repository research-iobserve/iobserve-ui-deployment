import Ember from 'ember';

const { Component, inject, computed } = Ember;

export default Component.extend({
    visualisationEvents: inject.service(),
    graph: null,
    entityDetails: null,
    classNameBindings: ['resizing'], // triggers deprecation warning for visualisationEvents.isResizing from SingleDeploymentDetails.endAnimation
    resizing: computed('visualisationEvents.isResizing', function() {
        return this.get('visualisationEvents.isResizing');
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
