import Model from 'ember-data/model';
import attr from 'ember-data/attr';

export default Model.extend({
    // id: attr('string') - not allowed to be listed by ember
    systemId: attr('string'),
    type: attr('string'),
    revisionNumber: attr('number'),
    changelogSequence: attr('number'),
    lastUpdate: attr('date')
});
