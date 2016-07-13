import Measurable from './measurable';
import attr from 'ember-data/attr';

const Model = Measurable.extend({
    name: attr('string')
});

Model.reopenClass({
    FIXTURES: [
       {
          "type":"nodeGroup",
          "id":"test-system123-nodeGroup-1",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "name":"CoCoME"
       }
    ]
});

export default Model;