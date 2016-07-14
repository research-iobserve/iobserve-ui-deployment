import Ember from 'ember';

export default Ember.Component.extend({
    entity: null,

    filteredProperties: Ember.computed('entity', 'entity._updated', function(){
        this.debug('entity', this.get('entity'));
        const properties = [];
        const entity = this.get('entity');
        entity.eachAttribute(property => {
            // do not show internal relations, also timeSeries gets plotted
            if(property.indexOf('Id') < 0 && property !== 'timeSeries' && property !== 'statusInformations') {
                properties.push({key: property, value: entity.get(property)});
            }
        });
        this.debug('properties', properties);
        return properties;
    })

});
