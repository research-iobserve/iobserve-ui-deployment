import Ember from 'ember';

const { computed } = Ember;

export default Ember.Component.extend({
    entity: null,
    ignoredProperties: [
        'Id$',
        'timeSeries',
        'statusInformations',
        'revisionNumber',
        'changelogSequence'
    ],
    filteredProperties: Ember.computed('entity', 'entity._updated', function(){
        this.debug('entity', this.get('entity'));
        const properties = [];
        const entity = this.get('entity');
        entity.eachAttribute(property => {
            // do not show internal relations, also timeSeries gets plotted
            if(!this.get('ignoredRegexp').some(regex => regex.test(property))) {
                properties.push({key: property, value: entity.get(property)});
            }
        });
        this.debug('properties', properties);
        return properties;
    }),
    ignoredRegexp: computed('ignoredProperties', function() {
        return this.get('ignoredProperties').map(prop => new RegExp(prop));
    })

});
