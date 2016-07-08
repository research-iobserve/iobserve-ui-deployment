import Ember from 'ember';
import EmberData from 'ember-data';
import Resolver from './resolver';
import loadInitializers from 'ember-load-initializers';
import config from './config/environment';

let App;

Ember.MODEL_FACTORY_INJECTIONS = true;


avoidEnumerableNativeExtensions(Array.prototype);
avoidEnumerableNativeExtensions(Function.prototype);
avoidEnumerableNativeExtensions(String.prototype);

App = Ember.Application.extend({
  modulePrefix: config.modulePrefix,
  podModulePrefix: config.podModulePrefix,
  Resolver
});

loadInitializers(App, config.modulePrefix);

export default App;

// TODO: just for development purposes
window.App = App;
window.Ember = Ember;
window.EmberData = EmberData;

// ember (via ember-runtime) sets native prototype enhancements like .property/.observer, but as enumerable
// we need to fix enumerability since it breaks cose-bilkent and sometimes cytoscape
function avoidEnumerableNativeExtensions(proto) {
    Object.keys(proto) // already gets all enumerables, no need to filter
        .map(key => {
            return { key, descriptor: Object.getOwnPropertyDescriptor(proto, key) };
        })
        .forEach(obj => {
            obj.descriptor.enumerable = false;
            Object.defineProperty(proto, obj.key, obj.descriptor);
        });
}