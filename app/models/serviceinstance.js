import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

const Model = BaseEntity.extend({
    name: attr('string'),
    nodeId: attr('string'),
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
          "name":"PostgreSQL",
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
          "name":"FrontEnd",
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