import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

export default BaseEntity.extend({
    name: attr(), // FIXME relation
    services: attr(), // FIXME relation
    ip: attr('string'),
    hostname: attr('string'),
    group: attr('string'), // TODO: relation?
});
