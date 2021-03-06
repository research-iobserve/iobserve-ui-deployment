import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType
});

Router.map(function() {
    this.route('home', {path: '/'});
    this.route('deployments', function() {
        this.route('single', {path: '/:systemId'}, function() {
            this.route('details', {path: '/:entityType/:entityId'}); // use model properties for automatic matching
        });
    });

    this.alias('architectures', '/architectures', 'deployments'); // uses ember-route-alias, inherits nested routes

    this.route('not-found', { path: '*path' });
});

export default Router;
