import Ember from 'ember';

export default Ember.Route.extend({
  model() {
    return {
        "nodes":[
          {"name":"a","width":60,"height":40},
          {"name":"b","width":60,"height":40},
          {"name":"c","width":60,"height":40},
          {"name":"d","width":60,"height":40},
          {"name":"e","width":60,"height":40},
          {"name":"f","width":60,"height":40},
          {"name":"g","width":60,"height":40}
        ],
        "links":[
          {"source":1,"target":2},
          {"source":2,"target":3},
          {"source":3,"target":4},
          {"source":0,"target":1},
          {"source":2,"target":0},
          {"source":3,"target":5},
          {"source":0,"target":5}
        ],
        "groups":[
          {"leaves":[0], "groups":[1]},
          {"leaves":[1,2]},
          {"leaves":[3,4]}
        ]
    };
  }
});