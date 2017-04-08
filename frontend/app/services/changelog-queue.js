import Ember from 'ember';

/**
 * Stores changelogs which were received via ChangelogStreamService and applies the {ChangelogParserService} if triggered externally
 *
 * @class ChangelogQueueService
 * @extends {Ember.Service}
 * @uses ChangelogStreamService
 * @uses ChangelogParserService
 */
export default Ember.Service.extend({
    changelogParser: Ember.inject.service(),
    _private: {
        queue: Ember.A()
    },
    reset() {
        this.get('_private.queue').clear();
    },
    append(changelogs){
        const queue = this.get('_private.queue');
        queue.pushObjects(changelogs);
    },
    apply() {
        this.get('changelogParser').parse(this.get('_private.queue'));
        this.reset();
    },
    available: Ember.computed('empty', function() {
        return !this.get('empty');
    }),
    size: Ember.computed('_private.queue.[]', function() {
        return this.get('_private.queue.length');
    }),
    empty: Ember.computed('_private.queue.[]', function() {
        return this.get('size') === 0;
    })
});