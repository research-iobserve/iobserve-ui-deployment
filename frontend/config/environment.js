/* jshint node: true */

module.exports = function(environment) {
    const ENV = {
        modulePrefix: 'iobserve-ui',
        environment: environment,
        baseURL: '/',
        locationType: 'auto',
        EmberENV: {
          FEATURES: {
            // Here you can enable experimental features on an ember canary build
            // e.g. 'with-controller': true
          }
          //EXTEND_PROTOTYPES: true // (true is default) See avoidEnumerableNativeExtensions in app.js
        },

        APP: {
            // Here you can pass flags/options to your application instance
            // when it is created
            API_ROOT: 'https://iobserve-api.herokuapp.com',
            WEBSOCKET_ROOT: 'wss://iobserve-api.herokuapp.com',
            WEBSOCKET_RECONNECT: false // not running on heroku/jetty-runner
        },
        flashMessageDefaults: {
            // flash message defaults
            timeout: 5000,
            extendedTimeout: 1000,
            priority: 200,
            sticky: false,
            showProgress: true
        }
    };


    if (environment === 'development') {
    // ENV.APP.LOG_RESOLVER = true;
    // ENV.APP.LOG_ACTIVE_GENERATION = true;
    // ENV.APP.LOG_TRANSITIONS = true;
    // ENV.APP.LOG_TRANSITIONS_INTERNAL = true;
    // ENV.APP.LOG_VIEW_LOOKUPS = true;
    ENV.APP.API_ROOT = 'http://192.168.48.222:8080';
    ENV.APP.WEBSOCKET_ROOT = 'ws://192.168.48.222:8080';
    ENV.APP.WEBSOCKET_RECONNECT = true;

    }

    if (environment === 'test') {
    // Testem prefers this...
    ENV.baseURL = '/';
    ENV.locationType = 'none';

    // keep test console output quieter
    ENV.APP.LOG_ACTIVE_GENERATION = false;
    ENV.APP.LOG_VIEW_LOOKUPS = false;

    ENV.APP.rootElement = '#ember-testing';
    }

    if (environment === 'production') {

    }

    return ENV;
};
