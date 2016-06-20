import { moduleForComponent, test } from 'ember-qunit';
import hbs from 'htmlbars-inline-precompile';

moduleForComponent('architecture-viewer', 'Integration | Component | architecture viewer', {
  integration: true
});

test('it renders', function(assert) {
  // Set any properties with this.set('myProperty', 'value');
  // Handle any actions with this.on('myAction', function(val) { ... });

  this.render(hbs`{{architecture-viewer}}`);

  assert.equal(this.$().text().trim(), '');

  // Template block usage:
  this.render(hbs`
    {{#architecture-viewer}}
      template block text
    {{/architecture-viewer}}
  `);

  assert.equal(this.$().text().trim(), 'template block text');
});
