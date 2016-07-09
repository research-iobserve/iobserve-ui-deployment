import Ember from 'ember';

export default Ember.Service.extend({
    store: Ember.inject.service(),
    parse(changelog) {
        this.debug('store', this.get('store'));
        this.debug('parsing changelog', changelog);
        const data = changelog.data;
        const store = this.get('store');
        const normalized = store.normalize('node', data); // using application serializer

        this.debug('normalized', normalized);
        store.push(normalized);
    }
});