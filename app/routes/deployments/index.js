import Ember from 'ember';

export default Ember.Route.extend({
    session: Ember.inject.service(), // loads services/session.js for systemId
    model () {
        this.set('session.systemId', null); // no system selected, remove from model requests
        return this.store.findAll('system').then((systems) => {
            this.debug(systems);
            return systems;
        });
    }
});
