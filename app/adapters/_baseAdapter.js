/**
 * Adapters will automatically be used for API/EmberData interaction, using adapters which
 * are have the same name as the model, with a fallback to Application adapter
 * @module adapters
 */
import Ember from 'ember';
import RESTAdapter from 'ember-data/adapters/rest';
// import FixtureAdapter from 'ember-data-fixture-adapter';
import ENV from 'iobserve-ui/config/environment';

/**
 * basic adapter for non-system based APIs (currently just System)
 *
 * @class BaseAdapter
 * @extends DS.RESTAdapter
 */
export default RESTAdapter.extend({
    /**
     * the hostname of the api
     *
     * @property host
     * @type {String}
     */
    host: ENV.APP.API_ROOT,

    /**
     * the namespace is a path prefix, used as a version prefix

     * @property host
     * @type {String}
     * @default v1
     */
    namespace: 'v1',

    /**
     * the injected session which stores the system id

     * @property host
     * @type {Session}
     * @readonly
     */
    session: Ember.inject.service()
});