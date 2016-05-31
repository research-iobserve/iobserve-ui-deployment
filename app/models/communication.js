import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

export default BaseEntity.extend({
    system: attr('string'), // TODO: relation?
    technology: attr('string'),
    source: attr(), // FIXME relation
    target: attr(), // FIXME relation
    instances: attr(), // FIXME relation
});
