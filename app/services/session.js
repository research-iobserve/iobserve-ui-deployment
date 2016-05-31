import Ember from 'ember';

/**
 * this service stores the global state of the system.
 * @property {String} systemId store the id of the system which is currently used for
 *      loading metamodel components
 */
export default Ember.Service.extend({
    systemId: null
});
