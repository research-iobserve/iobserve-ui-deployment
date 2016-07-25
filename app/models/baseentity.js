/**
 * All models which represent data structures for API interaction.
 * Uses EmberData
 * @module models
 */
import Model from 'ember-data/model';
import attr from 'ember-data/attr';

/**
 * Model for a system which encapsulates all other models
 * @class BaseEntityModel
 * @extends DS.Model
 * @public
 */
export default Model.extend({
    // id: attr('string') - not allowed to be listed by ember
    /**
     * id of the system this entity is contained in
     *
     * @property systemId
     * @type {String}
     * @public
     */
    systemId: attr('string'),

    /**
     * a unique type identifier, see API documentation
     *
     * @property type
     * @type {String}
     * @public
     * @readonly
     */
    type: attr('string'),

    /**
     * the revisionNumber in which this object was created or updated from the ChangelogParser
     *
     * @property revisionNumber
     * @type {Number}
     * @public
     */
    revisionNumber: attr('number'),

    /**
     * the changelogSequence in which this object was created or updated from the ChangelogParser
     *
     * @property changelogSequence
     * @type {Number}
     * @public
     */
    changelogSequence: attr('number'),

    /**
     * the date in which this object was created or updated from the ChangelogParser
     *
     * @property lastUpdate
     * @type {Date}
     * @public
     * @readonly
     */
    lastUpdate: attr('date')
});
