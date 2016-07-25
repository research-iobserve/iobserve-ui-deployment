import BaseEntity from './baseentity';
import attr from 'ember-data/attr';
import { memberAction } from 'ember-api-actions';

const Model = BaseEntity.extend({
    name: attr('string'),
    getRevision: memberAction({ path: 'revision', type: 'GET', urlType: 'findRecord'})
});

Model.reopenClass({
    FIXTURES: [{
      "type": "system",
      "id": "system123",
      "name": "Test System"
    }]
});

export default Model;