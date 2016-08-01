import Ember from 'ember';

export default Ember.Component.extend({
    visualisationEvents: Ember.inject.service(),
    graph: null,
    entityDetails: null,
    classNameBindings: ['resizing'],
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
        graphClick(rawEntity) {
            this.set('entityDetails', rawEntity);
        }
    }
});
