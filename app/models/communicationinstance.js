import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

export default BaseEntity.extend({
    sourceId: attr('string'),
    targetId: attr('string'),
    communicationId: attr('string')
});
