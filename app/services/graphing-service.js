import Ember from 'ember';

/**
 * parses a list of models and creates stores them
 */
export default Ember.Service.extend({
    createGraph(models) {
        this.debug('loaded models', models);
        return models;
    }
});
