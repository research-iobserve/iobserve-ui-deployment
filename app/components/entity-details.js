import Ember from 'ember';

export default Ember.Component.extend({
    entity: null,

    filteredProperties: Ember.computed('entity', 'entity._updated', function(){
        this.debug('entity', this.get('entity'));
        const properties = [];
        const entity = this.get('entity');
        entity.eachAttribute(property => {
            if(property.indexOf('Id') < 0) {
                properties.push({key: property, value: entity.get(property)});
            }
        });
        this.debug('properties', properties);
        return properties;
    })

});
