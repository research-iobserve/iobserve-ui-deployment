/*jshint node:true*/
/* global require, module */
var EmberApp = require('ember-cli/lib/broccoli/ember-app');

module.exports = function(defaults) {
  var app = new EmberApp(defaults, {
    // cose-bilkent breaks upon minification, see https://github.com/cytoscape/cytoscape.js-cose-bilkent/issues/4
    minifyJS: {
      enabled: false
    },
    'ember-cli-bootstrap-sassy': {
        'js': []
    }
  });

  // Use `app.import` to add additional libraries to the generated
  // output files.
  //
  // If you need to use different assets in different
  // environments, specify an object as the first parameter. That
  // object's keys should be the environment name and the values
  // should be the asset to use in that environment.
  //
  // If the library that you are including contains AMD or ES6
  // modules that you would like to import into your application
  // please specify an object with the list of modules as keys
  // along with the exports of each module as its value.

  app.import('bower_components/webcola/WebCola/cola.js');
  app.import('bower_components/Flot/jquery.flot.js'); // time-series-plot component
  app.import('bower_components/Flot/jquery.flot.time.js');

  return app.toTree();
};
