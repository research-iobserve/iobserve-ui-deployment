import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

export default BaseEntity.extend({
    name: attr('string'),
    system: attr(), // FIXME relation
    nodes: attr() // FIXME relation
});
