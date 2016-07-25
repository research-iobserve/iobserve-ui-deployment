import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

const Model = BaseEntity.extend({
    technology: attr('string'),
    sourceId: attr('string'),
    targetId: attr('string'),
    workload: attr('number')
});

export default Model;