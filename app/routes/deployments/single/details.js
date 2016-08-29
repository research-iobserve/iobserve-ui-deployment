import Ember from 'ember';

const { Route, inject, run, $ } = Ember;

/**
 * Loads the details for a specific entity.
 *
 * @class SingleDeploymentDetails
 * @extends Ember.Route
 * @module routes
 */
export default Route.extend({
    visualisationEvents: inject.service(),
    loadingState: inject.service(),
    /**
     * the duration of the extending sidebar animation, which is configured as transition in _architecture.scss.
     * Since we apparently cannot listen to css transitionEnd events, we have to manually wait the time.
     * Since this is fragile (since animations might lag on slower machines), we add a bit of buffer time.
     *
     * @type {Number} in milliseconds
     * @property animationDuration
     */
    animationDuration: 600, // ms
    model(params) {
        // will load data from cache and then update it, therefore the loading state is probably not visible
        const loading = this.store.findRecord(params.entityType.toLowerCase(), params.entityId); // TODO: match entity.type with model names
        this.get('loadingState').loadPromise(loading);
        return loading; // do not use returned promise from loadPromise since it will not change data
    },
    activate() {
        this.debug('route activated');
        $('body').addClass('extendedSidebar');
        this.notifyResize();
    },
    deactivate() {
        this.debug('route deactivated');
        $('body').removeClass('extendedSidebar');
        this.notifyResize();
    },
    notifyResize() {
        if(this.animationTimeout) { // in case a user navigated too fast
            run.cancel(this.animationTimeout);
            this.endAnimation();
        }

        // apparently we have to use events instead of regular property binding, because the cytoscape component won't subscribe correctly
        // this is probably because the component is already rendered
        this.get('visualisationEvents').startResizing();
        // triggers deprecation that properties where updated in didInsertElement through the classNameBinding in ArchitectureViewer
        this.animationTimeout = run.later(this, this.endAnimation, this.animationDuration);
    },
    endAnimation() {
        this.get('visualisationEvents').endResizing();
        this.animationTimeout = null;
    }
});
