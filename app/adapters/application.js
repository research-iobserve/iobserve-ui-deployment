import RESTAdapter from 'ember-data/adapters/rest';

export default RESTAdapter.extend({
    host: 'http://localhost:3000',
    namespace: 'v1'
});