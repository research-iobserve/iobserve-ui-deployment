import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

const Model = BaseEntity.extend({
    name: attr('string'),
    ip: attr('string'),
    hostname: attr('string'),
    nodeGroupId: attr('string')
});

Model.reopenClass({
    FIXTURES: [{
        "type":"node",
        "id":"test-system123-node-2",
        "changelogSequence":0,
        "lastUpdate":"2016-06-20T12:46:29.818+02:00",
        "revisionNumber":0,
        "systemId":"system123",
        "hostname":"host2",
        "ip":"10.0.0.2",
        "name":"Logicnode",
        "nodeGroupId":"test-system123-nodeGroup-1"
    },
    {
        "type":"node",
        "id":"test-system123-node-3",
        "changelogSequence":0,
        "lastUpdate":"2016-06-20T12:46:29.818+02:00",
        "revisionNumber":0,
        "systemId":"system123",
        "hostname":"host3",
        "ip":"10.0.0.2",
        "name":"Adapter",
        "nodeGroupId":"test-system123-nodeGroup-1"
    },
    {
        "type":"node",
        "id":"test-system123-node-4",
        "changelogSequence":0,
        "lastUpdate":"2016-06-20T12:46:29.818+02:00",
        "revisionNumber":0,
        "systemId":"system123",
        "hostname":"host4",
        "ip":"10.0.0.2",
        "name":"Database",
        "nodeGroupId":"test-system123-nodeGroup-1"
    },
    {
        "type":"node",
        "id":"test-system123-node-1",
        "changelogSequence":0,
        "lastUpdate":"2016-06-20T12:46:29.818+02:00",
        "revisionNumber":0,
        "systemId":"system123",
        "hostname":"test hostname",
        "ip":"10.0.0.1",
        "name":"WebNode",
        "nodeGroupId":"test-system123-nodeGroup-1"
    }]

})

export default Model;