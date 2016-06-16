import Ember from 'ember';
import RESTAdapter from 'ember-data/adapters/rest';

export default RESTAdapter.extend({
    host: 'http://localhost:8080', // TODO: replace with live url
    namespace: 'v1',
    session: Ember.inject.service()
});