import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

const Model = BaseEntity.extend({
    timeSeries: attr(),
    statusInformations: attr(),
    status: attr('string')
});

export default Model;
