import Ember from 'ember';
import RESTAdapter from 'ember-data/adapters/rest';
import FixtureAdapter from 'ember-data-fixture-adapter';

export default FixtureAdapter.extend({
    host: 'http://localhost:8080', // TODO: replace with live url
    namespace: 'v1',
    session: Ember.inject.service()
});