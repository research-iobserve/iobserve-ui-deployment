import Ember from 'ember';

/**
 * this service stores the global state of the system.
 *
 * @class  SessionService
 * @extends {Ember.Service}
 * @public
 */
export default Ember.Service.extend({
    /** systemId store the id of the system which is currently used for
     * loading metamodel components
     * @type {String}
     * @property systemId
     */
    systemId: null
});
