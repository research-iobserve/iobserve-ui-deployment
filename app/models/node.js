import Measurable from './measurable';
import attr from 'ember-data/attr';

/**
 * A `Node` is a execution environment for `Service`s
 * @class NodeModel
 * @extends MeasurableModel
 */
const Model = Measurable.extend({
    /**
     * Label for the the node
     *
     * @property name
     * @type {String}
     */
    name: attr('string'),
    /**
     * IP adress for this node
     *
     * @property ip
     * @type {String}
     */
    ip: attr('string'),

    /**
     * Hostname for this node
     *
     * @property hostname
     * @type {String}
     */
    hostname: attr('string'),

    /**
     * Id of the `NodeGroup` the node is contained in
     *
     * @property nodeGroupId
     * @type {String}
     */
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
        "name":"LogicNode",
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
        "name":"DataCenter",
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

});

export default Model;