import Ember from 'ember';

/**
 * Displays tuples are a table, one row with the key and one with the corresponding values
 *
 * @class KeyValueTableComponent
 * @extends Ember.Component
 */
export default Ember.Component.extend({
    /**
     * A list of tuples. It is a list to keep the order.
     * The tuple list is structured like the following example:
     * `[
     *   {key: "myProperty", value: "myValue"}
     * ]`
     *
     * @property tuples
     * @type {Array}
     * @required
     */
    tuples: null,

    /**
     * toggles whether the table header should be shown
     *
     * @property showHeader
     * @type {Boolean}
     * @default true
     */
    showHeader: true
});
