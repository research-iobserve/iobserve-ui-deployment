import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

export default BaseEntity.extend({
    name: attr('string'),
    ip: attr('string'),
    hostname: attr('string'),
    nodeGroupId: attr('string')
});
