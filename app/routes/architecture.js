import Ember from 'ember';

export default Ember.Route.extend({
  model() {
    return {
        id: 'root',
        labels: [{text: 'My Architecture'}],
        children: [{
            id: 'VM_Big',
            children: [{
                id: 'VM_Big>Auth'
            }, {
                id: 'VM_Big>User'
            }],
            edges: [{
                id: 'VM_Big>Auth->VM_Big>User',
                labels: [ { text: 'e1' } ],
                source: 'VM_Big>Auth',
                target: 'VM_Big>User'
            }]
        },
        {
            id: 'VM_1',
            children: [{
                id: 'VM_1>Auth',
                labels: [{text: "Auth 1"}]
            }]
        },
        {
            id: 'VM_2',
            children: [{
                id: 'VM_2>Auth',
                labels: [{text: "Auth 2"}]
            }]
        },
        {
            id: 'VM_3',
            children: [{
                id: 'VM_3>User',
                labels: [{text: "User 1"}]
            }]
        },
        {
            id: 'VM_4',
            children: [{
                id: 'VM_4>User',
                labels: [{text: "User 2"}]
            }]
        }],
        edges: [{
            id: 'VM_1>Auth->VM_3>User',
            source: 'VM_1>Auth',
            target: 'VM_3>User',
        }, {
            id: 'VM_2>Auth->VM_4>User',
            source: 'VM_2>Auth',
            target: 'VM_4>User',
        }]
    };
  }
});