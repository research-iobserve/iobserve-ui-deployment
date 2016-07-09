import JSONSerializer from 'ember-data/serializers/json';

/**
 * Serializer used for any backend interaction.
 * Used by Ember Data for rest interactions as well as by the changelog stream
 *
 * @class ApplicationSerializer
 * @extends DS.JSONSerializer
 */
export default JSONSerializer.extend({
    /**
     * Enhances the compatibility with the RESTAdapter
     * automatic detection of id and type does not work,
     * type property inside data json is not what Ember expects
     *
     * @param  {Model} Model the class of the ember model used for record creation
     * @param  {Object} data  raw object parsed from JSON API responses
     * @return {[type]}   normalized record payload data (not yet a record instance)
     */
    normalize(Model, data){
        const normalized = this._super.apply(this, arguments);
        normalized.id = data.id;
        normalized.type = data.type;
        return normalized;
    },
    /**
     * overwrites the default json serialisation such that serialised records always contain the id
     *
     * @param  {Model} record  the record from the Ember Data store
     * @param  {Object} options serialisation options
     * @return {Object}         a serialized version (plain JS object) of the record
     */
    serialize(record, options) {
        options = options || {};
        options.includeId = true;
        return this._super.call(this, record, options); // Get default serialization
    }
});