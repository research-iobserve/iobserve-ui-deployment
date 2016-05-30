import BaseEntity from './baseentity.js';
import attr from 'ember-data/attr';

export default BaseEntity.extend({
    name: attr('string'),
    node: attr(), // FIXME relation
    service: attr() // FIXME relation
});
