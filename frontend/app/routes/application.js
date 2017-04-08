import Ember from 'ember';

const { Route, inject } = Ember;

export default Route.extend({

    /**
     * Injected LoadingState service to handle loading. Set from route
     *
     * @property loadingState
     * @type {LoadingStateService}
     */
    loadingState: inject.service(),
    actions: {
        loading(transition) {
            this.get('loadingState').loadPromise(transition.promise);
        }
    }
});