import Ember from 'ember';

export default Ember.Component.extend({
    graph: null,
    layoutAlgorithm: 'cose',
    layoutAlgorithms: [
        "cose",
        "cola"
    ],
    init() {
        this._super();
        this.debug('init!', this.get('graph'), this.get('layoutAlgorithm'));
    },
    actions: {
        selectLayoutAlgorithm(value) {
            this.debug('value', value);
            this.set('layoutAlgorithm', value);
        }
    }
});
