import Ember from 'ember';

export default Ember.Service.extend({
    store: Ember.inject.service(),
    parse(changelogs) {
        this.debug('store', this.get('store'));
        this.debug('parsing changelogs', changelogs);
        changelogs.forEach(this.parseSingle.bind(this));
    },
    parseSingle(changelog) {
        const data = changelog.data;
        const store = this.get('store');
        const normalized = store.normalize('node', data); // using application serializer

        this.debug('normalized', normalized);
        store.push(normalized);
    }
});