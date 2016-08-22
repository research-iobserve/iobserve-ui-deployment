/**
 * Controllers can be considered components that are directly connected
 * to a route. They hold the state for the routes template
 *
 * @module controllers
 */
import Ember from 'ember';

const observables = [
    'nodes',
    'nodeGroups',
    'serviceInstances',
    'communicationInstances',
    'communications',
    'services',
];

/**
 * The use of this controller is required solely because Ember does not allow routes
 * to pass more than `model()` to templates, but we need to apply graph transformations.
 * @class SingleDeploymentController
 * @extends Ember.Controller
 * @uses SingleDeploymentRoute
 */
export default Ember.Controller.extend({
    deploymentGraphingService: Ember.inject.service(),
    architectureGraphingService: Ember.inject.service(),
    changelogQueue: Ember.inject.service(),
    loadingState: Ember.inject.service(),

    /**
     * A computed property which returns the current system in a cytoscape-compatble format.
     * Gets automatically updated when any of the instances changes, changes are notified via a pseudo property 'updated'
     *
     * @property graphModel
     * @type {CytoscapeGraph}
     */
    // using @each will also listen if the array itself changes. Can be quite expensive.
    graphModel: Ember.computed(`model.instances.{${observables.join(',')}}.@each._updated`, 'model.mode', function() {
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
        let graphingService;
        if(this.get('model.mode') === 'architectures') {
            graphingService = this.get('architectureGraphingService');
        } else {
            graphingService = this.get('deploymentGraphingService');
        }
        this.debug('creating graph', filteredInstances);
        return graphingService.createGraph(filteredInstances); // TODO: update instead of complete recalculation?
    }),
    actions: {
        /**
         * Apply the latest changes that are in queue for to update the visualisation.
         * Called from user interation
         * @method actions.applyQueueUpdates
         */
        applyQueueUpdates() {
            this.get('changelogQueue').apply();
        },
        loading(transition) {
            this.get('loadingState').loadPromise(transition.promise);
        }
    }
});