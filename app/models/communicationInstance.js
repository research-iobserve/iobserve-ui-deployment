import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

export default BaseEntity.extend({
    source: attr(), // FIXME relation
    target: attr(), // FIXME relation
    communication: attr(), // FIXME relation
});
