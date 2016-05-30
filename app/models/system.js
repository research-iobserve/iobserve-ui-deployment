import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

export default BaseEntity.extend({
    name: attr('string'),
    nodeGroups: attr('string'), // FIXME relation
    communications: attr('string'), // FIXME relation
    services: attr('string') // FIXME relation
});
