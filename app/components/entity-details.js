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
        const entity = this.get('entity');
        this.debug('entity', entity);
        const properties = [
            {key: 'id', value: entity.get('id')}
        ];
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
