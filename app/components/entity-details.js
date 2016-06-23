import Ember from 'ember';

export default Ember.Component.extend({
    entity: null,

    filteredProperties: function(){
        this.debug('entity', this.get('entity'));
        const properties = [];
        const entity = this.get('entity');
        this.get('entity').eachAttribute(
            (property) => {
                if(!(property.indexOf('Id') >= 0)){
                    properties.push({key: property, value: entity.get(property)});
                }
                });
        this.debug('properties', properties);
        return properties;
    }.property('entity')

});
