import Measurable from './measurable';
import attr from 'ember-data/attr';

/**
 * Represenation of a process of a `Service` running on a `Node`.
 * @class ServiceInstanceModel
 * @extends MeasurableModel
 */
const Model = Measurable.extend({
    /**
     * Name for the instance (same as from `Service`)
     *
     * @property name
     * @type {String}
     */
    name: attr('string'),

    /**
     * Id of the `Node` the service instance is running on
     *
     * @property nodeId
     * @type {String}
     */
    nodeId: attr('string'),

    /**
     * Id of the `Service` the instance is spawned for
     *
     * @property serviceId
     * @type {String}
     */
    serviceId: attr('string')
});

Model.reopenClass({
    FIXTURES: [
       {
          "type":"serviceInstance",
          "id":"test-system123-serviceInstance-4",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "name":"Inventory",
          "status": "WARNING",
          "nodeId":"test-system123-node-2",
          "serviceId":"test-system123-service-4"
       },
       {
          "type":"serviceInstance",
          "id":"test-system123-serviceInstance-5",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "name":"Data",
          "nodeId":"test-system123-node-3",
          "serviceId":"test-system123-service-5"
       },
       {
          "type":"serviceInstance",
          "id":"test-system123-serviceInstance-6",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "name":"Database",
          "nodeId":"test-system123-node-4",
          "serviceId":"test-system123-service-6"
       },
       {
          "type":"serviceInstance",
          "id":"test-system123-serviceInstance-1",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "name":"Frontend",
          "nodeId":"test-system123-node-1",
          "serviceId":"test-system123-service-1"
       },
       {
          "type":"serviceInstance",
          "id":"test-system123-serviceInstance-2",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "name":"WebService",
          "nodeId":"test-system123-node-2",
          "serviceId":"test-system123-service-2"
       },
       {
          "type":"serviceInstance",
          "id":"test-system123-serviceInstance-3",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123",
          "name":"CashDesk",
          "nodeId":"test-system123-node-2",
          "serviceId":"test-system123-service-3"
       }
    ]
});

export default Model;