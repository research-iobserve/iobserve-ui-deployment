import Measurable from './measurable';
import attr from 'ember-data/attr';


/**
 * represents a single instance of a `Communication`.
 *
 * @class CommunicationInstance
 * @extends Measurable
 */
const Model = Measurable.extend({
    /**
     * id of the `Communication` which this is an instance of
     *
     * @property communicationId
     * @type {String}
     */
    communicationId: attr('string'),

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
        "type": "communicationInstance",
        "id": "test-system123-communicationInstance-1",
        "changelogSequence": 0,
        "lastUpdate": "2016-06-22T16:49:49.146+02:00",
        "revisionNumber": 0,
        "systemId": "system123",
        "communicationId": "test-system123-communication-1",
        "sourceId": "test-system123-serviceInstance-1",
        "targetId": "test-system123-serviceInstance-2",
        "workload": 10
      },
      {
        "type": "communicationInstance",
        "id": "test-system123-communicationInstance-2",
        "changelogSequence": 0,
        "lastUpdate": "2016-06-22T16:49:49.146+02:00",
        "revisionNumber": 0,
        "systemId": "system123",
        "communicationId": "test-system123-communication-2",
        "sourceId": "test-system123-serviceInstance-2",
        "targetId": "test-system123-serviceInstance-3",
        "workload": 20
      },
      {
        "type": "communicationInstance",
        "id": "test-system123-communicationInstance-3",
        "changelogSequence": 0,
        "lastUpdate": "2016-06-22T16:49:49.146+02:00",
        "revisionNumber": 0,
        "systemId": "system123",
        "communicationId": "test-system123-communication-3",
        "sourceId": "test-system123-serviceInstance-3",
        "targetId": "test-system123-serviceInstance-4",
        "workload": 10
      },
      {
        "type": "communicationInstance",
        "id": "test-system123-communicationInstance-4",
        "changelogSequence": 0,
        "lastUpdate": "2016-06-22T16:49:49.146+02:00",
        "revisionNumber": 0,
        "systemId": "system123",
        "communicationId": "test-system123-communication-4",
        "sourceId": "test-system123-serviceInstance-3",
        "targetId": "test-system123-serviceInstance-5",
        "workload": 15
      },
      {
        "type": "communicationInstance",
        "id": "test-system123-communicationInstance-5",
        "changelogSequence": 0,
        "lastUpdate": "2016-06-22T16:49:49.146+02:00",
        "revisionNumber": 0,
        "systemId": "system123",
        "communicationId": "test-system123-communication-5",
        "sourceId": "test-system123-serviceInstance-4",
        "targetId": "test-system123-serviceInstance-5",
        "workload": 10
      },
      {
        "type": "communicationInstance",
        "id": "test-system123-communicationInstance-6",
        "changelogSequence": 0,
        "lastUpdate": "2016-06-22T16:49:49.146+02:00",
        "revisionNumber": 0,
        "systemId": "system123",
        "communicationId": "test-system123-communication-6",
        "sourceId": "test-system123-serviceInstance-5",
        "targetId": "test-system123-serviceInstance-6",
        "workload": 15
      }
    ]
});

export default Model;
