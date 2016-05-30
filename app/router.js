import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType
});

Router.map(function() {
  this.route('home', {path: '/'});
  this.route('architecture', {path: '/architecture'});
  this.route('cola', {path: '/cola'});
  this.route('cytoscape');
});

export default Router;
