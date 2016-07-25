import Measurable from './measurable';
import attr from 'ember-data/attr';

/**
 * Arbitrary logical grouping of several nodes.
 *
 * @class NodeGroupModel
 * @extends MeasurableModel
 */
const Model = Measurable.extend({
    /**
     * Label for this group
     *
     * @property name
     * @type {String}
     */
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