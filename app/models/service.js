import Measurable from './measurable';
import attr from 'ember-data/attr';

/**
 * A software application fullfilling a function for the system.
 *
 * @class ServiceModel
 * @extends MeasurableModel
 */
const Model = Measurable.extend({
    /**
     * Label for the service
     *
     * @property name
     * @type {String}
     */
    name: attr('string'),

    /**
     * Description of the function that this service fullfulls
     *
     * @property description
     * @type {String}
     */
    description: attr('string')
});

Model.reopenClass({
    FIXTURES: [
       {
          "type":"service",
          "id":"test-system123-service-1",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123"
       },
       {
          "type":"service",
          "id":"test-system123-service-2",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123"
       },
       {
          "type":"service",
          "id":"test-system123-service-3",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123"
       },
       {
          "type":"service",
          "id":"test-system123-service-4",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123"
       },
       {
          "type":"service",
          "id":"test-system123-service-5",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123"
       },
       {
          "type":"service",
          "id":"test-system123-service-6",
          "changelogSequence":0,
          "lastUpdate":"2016-06-20T12:46:29.818+02:00",
          "revisionNumber":0,
          "systemId":"system123"
       }
    ]
});

export default Model;