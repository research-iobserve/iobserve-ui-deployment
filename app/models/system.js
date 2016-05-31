import BaseEntity from './baseentity';
import attr from 'ember-data/attr';
import DS from 'ember-data';

export default BaseEntity.extend({
    name: attr('string')
});
