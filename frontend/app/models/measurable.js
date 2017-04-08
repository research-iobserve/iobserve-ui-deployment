import BaseEntity from './baseentity';
import attr from 'ember-data/attr';

/**
 * Model for a system which encapsulates all
 * @class MeasurableModel
 * @extends BaseEntityModel
 * @public
 */
const Model = BaseEntity.extend({
    /**
     * List of TimeSeries objects
     *
     * @property timeSeries
     * @type {Array|TimeSeries}
     */
    timeSeries: attr(),

    /**
     * Arbitrary list of key value tuples
     * @property statusInformations
     * @type {Array|StatusInformation}
     */
    statusInformations: attr(),

    /**
     * Status of this entity. `NORMAL`, `WARNING` or `FAIL`
     *
     * @property status
     * @type {String}
     * @default NORMAL
     */
    status: attr('string') // NORMAL, WARNING, FAIL
});

export default Model;

// FOR DOCUMENTATION ONLY
// jshint unused:false

/**
 * An object that does not have an own API endpoint, but is instead only
 * accessible because it is nested within other entities
 *
 * @class NestedMeasurement
 */
const NestedMeasurement = {
    /**
     * as Ember Data Models each NestedMeasurement also has a unique id,
     * but is not an Ember Data Model, since it is not synchronized with any API endpoint
     *
     * @property id
     * @type {String}
     */
    id: null,
    /**
     * id of the entity which contains this object.
     * @property parentId
     * @type {String}
     */
    parentId: null
};

/**
 * TimeSeries is a timestamp-bound list of plottable values.
 * The values are collected or generated from analytics, monitoring or simulation.
 * The list might be appended in the future.
 *
 * @class TimeSeries
 * @extends NestedMeasurement
 */
const TimeSeries = {
    /**
     * Description for the values
     *
     * @property label
     * @type {String}
     */
    label: null,

    /**
     * Label for the values, e.g. the name of the unit, like `ms` for milliseconds.
     * @property valueLabel
     * @type {String}
     */
    valueLabel: null,

    /**
     * List of all values
     * @type {Array|TimeSeriesElement}
     */
    series: null
};

/**
 * Representation of one `TimeSeries` value.
 *
 * @class TimeSeriesElement
 * @extends NestedMeasurement
 */
const TimeSeriesElement = {
    /**
     * the measured/generated value for a specific time
     * @property value
     * @type {Number}
     */
    value: null,
    /**
     * the time the measurement took place, serialized as a string
     * @type {String|Date}
     */
    timestamp: null
};

/**
 * StatusInformation is a timestamp-bound list of key-value tuples.
 * The stored values should represent more or less static information for an entity.
 *
 * @class StatusInformation
 * @extends NestedMeasurement
 * @public
 */
const StatusInformation = {

    /**
     * time of creation
     * @property timeStamp
     * @type {Date}
     */
    timeStamp: null,

    /**
     * a descriptional key for the data
     * @property key
     * @type {String}
     */
    key: null,

    /**
     * Arbitrary data stored
     * @property value
     * @type {String}
     */
    value: null,
};