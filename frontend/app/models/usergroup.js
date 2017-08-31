import Measurable from './measurable';
import attr from 'ember-data/attr';

/**
 * 
 *
 * @class UserGroupModel
 * @extends MeasurableModel
 */
const Model = Measurable.extend({
    /**
     * Label for this usergroup
     *
     * @property name
     * @type {String}
     */
    name: attr('string'),


    /**
     * List of services invoked by the usergroup
     */
    calledServices: attr()
});

Model.reopenClass({
    FIXTURES: [
       {
          "type":"userGroup",
          "id":"test-system123-userGroup-1",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "name":"CoCoME-Usergroup",
          "calledServices": [{"targetId":"test-calledService-1"}, {"targetId":"test-calledService-2"},{"targetId":"test-calledService-3"}]
       }
    ]
});

export default Model;