import JSONSerializer from 'ember-data/serializers/json';

export default JSONSerializer.extend({
    serialize: function(record, options) {
        options = options || {};
        options.includeId = true;
        return this._super.call(this, record, options); // Get default serialization
    }
});