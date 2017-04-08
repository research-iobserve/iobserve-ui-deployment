import Ember from 'ember';

export default Ember.Route.extend({
    model() {
        return this.store.findAll('system').then((systems) => {
            console.log('loaded systems', systems);
            return systems;
        });
    }
});
