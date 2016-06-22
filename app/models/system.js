import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

const Model = BaseEntity.extend({
    name: attr('string')
});

Model.reopenClass({
    FIXTURES: [{
      "type": "system",
      "id": "system123",
      "name": "Test System"
    }]
});

export default Model;