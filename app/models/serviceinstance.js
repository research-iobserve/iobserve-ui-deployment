import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

export default BaseEntity.extend({
    name: attr('string'),
    nodeId: attr('string'),
    serviceId: attr('string')
});
