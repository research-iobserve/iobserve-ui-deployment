import Ember from 'ember';

const { Service, computed } = Ember;

/**
 * Central service to trigger the loading state for the application.
 * Gives the application the possibility to block actions while the application is loading a route.
 *
 * @class LoadingStateService
 * @extends {Ember.Service}
 */
export default Service.extend({
    /**
     * Computed property which states if the application is loading a route.
     * Can be subscribed to.
     *
     * @type {Boolean}
     * @property isLoading
     */
    isLoading: computed('_loadingCounter', function() {
        return this.get('_loadingCounter') > 0;
    }),

    /**
     * Negation of `isLoading`.
     *
     * @type {Boolean}
     * @property isNotLoading
     */
    isNotLoading: computed('isLoading', function() {
        return !this.get('isLoading');
    }),

    /**
     * States if the application is loading a route.
     * Can be subscribed to.
     *
     * @type {Number}
     * @property _loadingCounter
     * @private
     */
    _loadingCounter: 0,

    /**
     * starts the loading process. Increments internal counter.
     *
     * @method startLoading
     */
    startLoading() {
        this.incrementProperty('_loadingCounter');
        this.debug('starting to load', this.get('_loadingCounter'));
    },

    /**
     * Automagically handle the loading state for promises. Do not call `stopLoading` after that.
     * @method loadPromise
     * @param  {Promise} promise
     * @return promise that resolves after the loading was stopped
     */
    loadPromise(promise) {
        this.startLoading();
        return promise.finally(() => {
            this.stopLoading();
        });
    },

    /**
     * Signals that a loading was stopped. Decreases internal counter.
     * @method stopLoading
     */
    stopLoading() {
        this.set('_loadingCounter', Math.max(0, this.get('_loadingCounter') - 1));
        this.debug('stopped loading', this.get('_loadingCounter'));
    }
});