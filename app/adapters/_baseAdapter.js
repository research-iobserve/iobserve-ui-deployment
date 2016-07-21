import Ember from 'ember';
import RESTAdapter from 'ember-data/adapters/rest';
// import FixtureAdapter from 'ember-data-fixture-adapter';
import ENV from 'iobserve-ui/config/environment';

export default RESTAdapter.extend({
    host: ENV.APP.API_ROOT,
    namespace: 'v1',
    session: Ember.inject.service()
});