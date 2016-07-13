import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

const Model = BaseEntity.extend({
    timeSeries: attr(),
    statusInformations: attr()
});

export default Model;
