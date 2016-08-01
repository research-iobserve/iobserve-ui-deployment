import Ember from 'ember';

/**
 * Loads all known systems
 *
 * @class DeploymentsRoute
 * @extends Ember.Route
 * @uses SessionService
 * @module routes
 */
export default Ember.Route.extend({
    session: Ember.inject.service(), // loads services/session.js for systemId

    /**
     * gets all systems
     *
     * @method model
     * @return {Promise | Ember.Array | SystemModel}
     */
    model () {
        this.set('session.systemId', null); // no system selected, remove from model requests
        return this.store.findAll('system').then((systems) => {
            this.debug(systems);
            return systems;
        });
    }
});
