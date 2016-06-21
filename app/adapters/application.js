import BaseAdapter from './_baseAdapter';
import UrlTemplates from "ember-data-url-templates";

/**
 * @param  {UrlTemplate}
 * @param  {Object} configuration for templates
 * @return {Adapter} adapter that is used for all metamodel models
 */
export default BaseAdapter.extend(UrlTemplates, {
    urlTemplate: '{+host}/{+namespace}/{pathForType}{/id}',
    findAllUrlTemplate: '{+host}/{+namespace}/systems{/systemId}/{pathForType}/{?query*}',

    urlSegments: {
        systemId() {
          return this.get('session.systemId');
        }
    }
});