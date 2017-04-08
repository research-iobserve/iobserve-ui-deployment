import BaseEntity from './baseentity';
import attr from 'ember-data/attr';
import { memberAction } from 'ember-api-actions';

/**
 * Model for a system which encapsulates all
 * @class SystemModel
 * @extends BaseEntityModel
 */
const Model = BaseEntity.extend({
    /**
     * name of the system
     * @type {String}
     * @property name
     */
    name: attr('string'),
    /**
     * loads the current revision for the system instance from the server (without caching)
     * @method getRevision
     * @return {Promise|RevisionResponse} the current revision of the system
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

// FOR DOCUMENTATION ONLY
/**
 * Response object from a revision class. This is only for improving the documentation, there is no real class existing.
 * @class RevisionResponse
 */
const Revision = {
    // jshint unused:false
    /**
     * the revision which was used when creating the entity
     * @property revisionNumber
     * @type Number
     * @readonly
     */
    revisionNumber: null,

    /**
     * The date (as a String) when the revision was changed the last time (server time)
     *
     * @property lastUpdate
     * @type String|Date
     * @readonly
     */
    lastUpdate: null,
    /**
     * The sequence number, at which position of the applied list of changelogs was the last update
     * @property changelogSequence
     * @type Number
     * @readonly
     */
    changelogSequence: null

};