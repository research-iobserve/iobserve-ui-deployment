import Ember from 'ember';

export default Ember.Service.extend({
    store: Ember.inject.service(),
    parse(changelogs) {
        this.debug('store', this.get('store'));
        this.debug('parsing changelogs', changelogs);
        changelogs.forEach(this.parseSingle.bind(this));
    },
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
        UPDATE(changelog) { // FIXME: does not update view!
            const data = changelog.data;
            const oldRecord = this.get('store').peekRecord(data.type, data.id);
            this.debug('oldRecord', oldRecord);
            if(!oldRecord) {
                throw new Error(`old record for update operation not found! Id: ${data.id}`);
            }
            oldRecord.setProperties(data);
        },
        DELETE(changelog) {
            const data = changelog.data;
            const oldRecord = this.get('store').peekRecord(data.type, data.id);
            if(!oldRecord) {
                throw new Error(`old record for update operation not found! Id: ${data.id}`);
            }
            this.get('store').unloadRecord(oldRecord);
        },
        APPEND(changelog) {
            throw new Error('not yet implemented');
        }
    }
});