import Ember from 'ember';

const { Controller, inject } = Ember;
/**
 * Global controller, providing functionality to global templates.
 * @class ApplicationController
 * @extends Ember.Controller
 */
export default Controller.extend({
    /**
     * Injected FlashMessages service from addon
     *
     * @property flashMessage
     * @type {FlashMessageService}
     */
    flashMessages: inject.service()
});