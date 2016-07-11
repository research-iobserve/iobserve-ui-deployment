import Ember from 'ember';

export default Ember.Route.extend({
    visualisationEvents: Ember.inject.service(),
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
        return this.store.findRecord(params.entityType.toLowerCase(), params.entityId); // TODO: match entity.type with model names
    },
    activate() {
        this.debug('route activated');
        Ember.$('body').addClass('extendedSidebar');
        this.notifyResize();
    },
    deactivate() {
        this.debug('route deactivated');
        Ember.$('body').removeClass('extendedSidebar');
        this.notifyResize();
    },
    notifyResize() {
        if(this.animationTimeout) { // in case a user navigated too fast
            this.endAnimation();
        }
        this.get('visualisationEvents').trigger('resize:start');
        this.animationTimeout = setTimeout(this.endAnimation.bind(this), this.animationDuration);
    },
    endAnimation() {
        this.get('visualisationEvents').trigger('resize:end');
        this.animationTimeout = null;
    }
});
