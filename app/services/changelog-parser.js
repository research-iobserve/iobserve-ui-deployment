import Ember from 'ember';

// TODO: update cytoscape instead of complete rerender
/**
 * The changelog parser takes a changelog (as a plain JS object) and updates the internal
 * state of the Ember Data store automatically. Supports CREATE, UPDATE, DELETE and APPEND operations
 *
 * @class ChangelogParserService
 * @extends {Ember.Service}
 */
export default Ember.Service.extend({
    store: Ember.inject.service(),

    /**
     * Parse a list of changelogs
     * @param  {Array} changelogs List of changelogs, as plain JavaScript objects
     * @method parse
     * @public
     */
    parse(changelogs) {
        this.debug('store', this.get('store'));
        this.debug('parsing changelogs', changelogs);
        changelogs.forEach(this.parseSingle.bind(this));
    },

    /**
     * Parse a single changelog
     *
     * @param  {Object} changelog changelog as plain JavaScript object, see backend documentation for data structure
     * @method parseSingle
     * @public
     */
    parseSingle(changelog) {
        const operation = this.get(`operations.${changelog.operation}`);
        if(!operation) {
            throw new Error(`received an invalid changelog, unknown operation: ${changelog.operation}`);
        }
        operation.call(this, changelog);
    },
    operations: {
        CREATE(changelog) {
            const store = this.get('store');
            const data = changelog.data;
            const normalized = store.normalize(data.type, data); // using application serializer
            store.push(normalized);
        },
        UPDATE(changelog) {
            const data = changelog.data;
            const oldRecord = this.get('store').peekRecord(data.type, data.id);
            this.debug('oldRecord', oldRecord);
            if(!oldRecord) {
                throw new Error(`old record for update operation not found! Id: ${data.id}`);
            }
            oldRecord.setProperties(data);
            oldRecord.notifyPropertyChange('_updated'); // notify about pseudo property, can be listened on (see deployments/single controller)
        },
        DELETE(changelog) {
            const data = changelog.data;
            const oldRecord = this.get('store').peekRecord(data.type, data.id);
            if(!oldRecord) {
                throw new Error(`old record for update operation not found! Id: ${data.id}`);
            }
            this.get('store').unloadRecord(oldRecord);
        },
        APPEND(/*changelog*/) {
            throw new Error('not yet implemented');
        }
    }
});