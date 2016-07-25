import BaseEntity from './baseentity';
import attr from 'ember-data/attr';
import { memberAction } from 'ember-api-actions';

/**
 * Model for a system which encapsulates all
 * @class System
 * @extends BaseEntity
 * @public
 */
const Model = BaseEntity.extend({
    /**
     * name of the system
     * @type {String}
     * @property name
     * @for System
     * @public
     */
    name: attr('string'),
    /**
     * loads the current revision for the system instance from the server (without caching)
     * @type {Promise|RevisionResponse} plain JS object containing the revisionNumber (number), lastUpdate (Date string), changelogSequence (number)
     */
    getRevision: memberAction({ path: 'revision', type: 'GET', urlType: 'findRecord'})
});

Model.reopenClass({
    FIXTURES: [{
      "type": "system",
      "id": "system123",
      "name": "Test System"
    }]
});

export default Model;

/**
 * Response object from a revision class. This is only for improving the documentation, there is no real class existing.
 * @class RevisionResponse
 * @public
 */
const Revision = {
    // jshint unused:false
    /**
     *
     * @property revisionNumber
     * @type {Number}
     * @readonly
     */
    revisionNumber: null,

    /**
     * @property lastUpdate
     * @type {String|Date}
     * @readonly
     */
    lastUpdate: null,
    /**
     * @property changelogSequence
     * @type {Number}
     * @readonly
     */
    changelogSequence: null

};