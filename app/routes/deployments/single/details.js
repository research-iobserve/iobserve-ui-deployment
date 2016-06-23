import Ember from 'ember';

export default Ember.Route.extend({
    model(params) {
        return this.store.findRecord(params.entityType.toLowerCase(), params.entityId); // TODO: match entity.type with model names
    }
});
