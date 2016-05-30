import BaseEntity from './baseentity.js';
import attr from 'ember-data/attr';

export default BaseEntity.extend({
    source: attr(), // FIXME relation
    target: attr(), // FIXME relation
    communication: attr(), // FIXME relation
});
