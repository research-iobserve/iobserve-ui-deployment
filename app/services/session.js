import Ember from 'ember';

/**
 * this service stores the global state of the system.
 *
 * @class SessionService
 * @extends Ember.Service
 * @public
 */
export default Ember.Service.extend({
    /** system stores the system which is currently used for
     * loading metamodel components
     * @type {System}
     * @property system
     */
    system: null
});
