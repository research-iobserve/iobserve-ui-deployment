import BaseAdapter from './_baseAdapter';
import UrlTemplates from "ember-data-url-templates";

/**
 * Basic Adapter for all models, except System.
 * Automatically uses the id of the current system in the URL path
 *
 * @class ApplicationAdapter
 * @param  {UrlTemplate}
 * @param  {Object} configuration for templates
 * @return {Adapter} adapter that is used for all metamodel models
 */
export default BaseAdapter.extend(UrlTemplates, {
    /**
     * template for building the url for requests.
     * See https://github.com/amiel/ember-data-url-templates
     *
     * @property urlTemplate
     * @type {String}
     */
    urlTemplate: '{+host}/{+namespace}/{pathForType}{/id}',

    /**
     * Automatically uses the id of the current system
     * Template for building the findAll url for requests.
     * See https://github.com/amiel/ember-data-url-templates.
     *
     * @property urlTemplate
     * @type {String}
     */
    findAllUrlTemplate: '{+host}/{+namespace}/systems{/systemId}/{pathForType}/{?query*}',

    /**
     * segment which can be used by referencing the name in the template syntax.
     * See https://github.com/amiel/ember-data-url-templates
     * @type {Object}
     */
    urlSegments: {
        /**
         * gets the current id of the system from the session service
         * @return {String} the id of the system
         * @method urlSegments.systemId
         */
        systemId() {
          return this.get('session.system.id');
        }
    }
});