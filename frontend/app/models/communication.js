import Measurable from './measurable';
import attr from 'ember-data/attr';

/**
 * represents an abstract communication which groups multiple CommunicationInstances
 * @class Communication
 * @extends Measurable
 */
const Model = Measurable.extend({
    /**
     * name of the technology used for communication
     * @property technology
     * @type String
     */
    technology: attr('string'),
    /**
     * id of the sending ServiceInstance
     * @property sourceId
     * @type String
     */
    sourceId: attr('string'),

    /**
     * id of the receiving ServiceInstance
     * @property targetId
     * @type String
     */
    targetId: attr('string'),

    /**
     * The amount of workload. Shows how many requests where sent via this connection
     * @property workload
     * @type Number
     */
    workload: attr('number')
});

Model.reopenClass({
    FIXTURES: [
       {
          "type":"communication",
          "id":"test-system123-communication-1",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "sourceId":"test-system123-service-1",
          "targetId":"test-system123-service-2",
          "technology":"REST"
       },
       {
          "type":"communication",
          "id":"test-system123-communication-2",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "sourceId":"test-system123-service-2",
          "targetId":"test-system123-service-3",
          "technology":"TCP/IP"
       },
       {
          "type":"communication",
          "id":"test-system123-communication-3",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "sourceId":"test-system123-service-3",
          "targetId":"test-system123-service-4",
          "technology":"TCP/IP"
       },
       {
          "type":"communication",
          "id":"test-system123-communication-4",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "sourceId":"test-system123-service-3",
          "targetId":"test-system123-service-5",
          "technology":"TCP/IP"
       },
       {
          "type":"communication",
          "id":"test-system123-communication-5",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "sourceId":"test-system123-service-4",
          "targetId":"test-system123-service-5",
          "technology":"TCP/IP"
       },
       {
          "type":"communication",
          "id":"test-system123-communication-6",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "sourceId":"test-system123-service-5",
          "targetId":"test-system123-service-6",
          "technology":"TCP/IP"
       }
    ]
});

export default Model;
