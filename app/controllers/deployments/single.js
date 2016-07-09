import Ember from 'ember';

const observables = [
    'nodes',
    'nodeGroups',
    'serviceInstances',
    'communicationInstances'
];

export default Ember.Controller.extend({
    graphingService: Ember.inject.service(),
    changelogQueue: Ember.inject.service(),

    init() {
        this.debug('initializing controller');
    },
    // gets automatically updated when any of the instances changes (updated from changelog stream)
    graphModel: Ember.computed(`model.instances.{${observables.join(',')}}.[]`, function() {
        const systemId = this.get('model.systemId');
        const instances = this.get('model.instances');
        /*
         * Since we use findAll to not load ALL instances but only for a specific system,
         * Ember would cache any instances by any system in the store,
         * this might lead to unexpected behavior after navigating to multiple systems/deployments.
         * Solution: filter out invalid
         */
        const filteredInstances = {};
        Object.keys(instances).map((key) => {
            filteredInstances[key] = instances[key].filterBy('systemId', systemId);
        });

        this.debug('creating graph', filteredInstances);
        return this.get('graphingService').createGraph(filteredInstances); // TODO: update instead of complete recalculation?
    }),
    actions: {
        applyQueueUpdates() {
            this.get('changelogQueue').apply();
        }
    }
});