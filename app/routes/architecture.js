import Ember from 'ember';

export default Ember.Route.extend({
  model() {
    return {
        id: 'root',
        labels: [{text: 'My Architecture'}],
        'properties': {
            'direction': 'RIGHT',
            'spacing': 10,
            'de.cau.cs.kieler.aspectRatio': 1.7,
            'de.cau.cs.kieler.separateConnComp': true,
            'separateConnComp': true
        },
        children: [{
            id: 'VM_Profile',
            children: [{
                id: 'VM_Profile>Database',
                labels: [{text: 'Profile DB'}],
                width: 20,
                height: 20
            }]
        },
        {
            id: 'VM_Big',
            children: [{
                id: 'VM_Big>Auth',
                width: 20,
                height: 20
            }, {
                id: 'VM_Big>User',
                width: 20,
                height: 20
            }, {
                id: 'VM_Big>User2',
                width: 20,
                height: 20
            }, {
                id: 'VM_Big>User3',
                width: 20,
                height: 20
            }, {
                id: 'VM_Big>User4',
                width: 20,
                height: 20
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
                labels: [{text: 'Auth 1'}],
                width: 20,
                height: 20
            }]
        },
        {
            id: 'VM_2',
            children: [{
                id: 'VM_2>Auth',
                labels: [{text: 'Auth 2'}],
                width: 20,
                height: 20
            }]
        },
        {
            id: 'VM_3',
            children: [{
                id: 'VM_3>User',
                labels: [{text: 'User 1'}],
                width: 20,
                height: 20
            }]
        },
        {
            id: 'VM_4',
            children: [{
                id: 'VM_4>User',
                labels: [{text: 'User 2'}],
                width: 20,
                height: 20
            }]
        },
        {
            id: 'VM_5',
            children: [{
                id: 'VM_5>User',
                labels: [{text: 'User 2'}],
                width: 20,
                height: 20
            }]
        },
        {
            id: 'VM_6',
            children: [{
                id: 'VM_6>User',
                labels: [{text: 'User 2'}],
                width: 20,
                height: 20
            }]
        },
        {
            id: 'VM_7',
            children: [{
                id: 'VM_7>User',
                labels: [{text: 'User 2'}],
                width: 20,
                height: 20
            }]
        },
        {
            id: 'VM_8',
            children: [{
                id: 'VM_8>User',
                labels: [{text: 'User 2'}],
                width: 20,
                height: 20
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
        } /*{
            id: 'VM_Big>User->VM_Profile>Database',
            source: 'VM_Big>User',
            target: 'VM_Profile>Database',
        }, {
            id: 'VM_2>Auth->VM_Profile>Database',
            source: 'VM_2>Auth',
            target: 'VM_Profile>Database',
        }, {
            id: 'VM_1>Auth->VM_Profile>Database',
            source: 'VM_1>Auth',
            target: 'VM_Profile>Database',
        }, {
            id: 'VM_3>User->VM_Profile>Database',
            source: 'VM_3>User',
            target: 'VM_Profile>Database',
        }, {
            id: 'VM_4>User->VM_Profile>Database',
            source: 'VM_4>User',
            target: 'VM_Profile>Database',
        }, {
            id: 'VM_5>User->VM_Profile>Database',
            source: 'VM_5>User',
            target: 'VM_Profile>Database',
        }, {
            id: 'VM_6>User->VM_Profile>Database',
            source: 'VM_6>User',
            target: 'VM_Profile>Database',
        }, {
            id: 'VM_7>User->VM_Profile>Database',
            source: 'VM_7>User',
            target: 'VM_Profile>Database',
        }, {
            id: 'VM_8>User->VM_Profile>Database',
            source: 'VM_8>User',
            target: 'VM_Profile>Database',
        }*/]
    };
  }
});