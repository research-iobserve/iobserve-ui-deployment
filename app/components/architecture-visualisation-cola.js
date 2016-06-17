import Ember from 'ember';
import d3 from 'd3';
import cola from 'npm:webcola';
import _ from 'npm:lodash';

export default Ember.Component.extend({
    init: function() {
        this._super();
        const self = this;
        const log = self.debug.bind(self);
        log('loaded');
        // this.interval = setInterval(function() {
        //     const graph = self.get('graph');

        //     log('update interval called', graph);

        //     if(!graph) {
        //         return; // eventual consistent
        //     }
        //     const edges = graph.edges || [];
        //     const index = Math.floor(Math.random()*edges.length);
        //     if(Math.random() > 0.5) {
        //         edges.splice(index, 1);
        //     } else { // FIXME: does not render updates
        //         const children = _.get(graph, 'children', []);
        //         const randomNodeIndex = Math.floor(Math.random()*children.length);
        //         const randomNodeId = children[randomNodeIndex].id;
        //         graph.edges = _.set(edges, `${index}.target`, randomNodeId);
        //         log(`updating random edge (${index}) to target a random node id ${randomNodeId}`, randomNodeIndex, edges);
        //     }
        //     self.set('graph', graph);
        //     self.renderGraph();
        // }, 1000);
    },
    willDestroyElement() {
        clearInterval(this.interval);
    },
    didInsertElement: function() {
        const log = this.debug.bind(this);
        const width = window.innerWidth;
        const height = window.innerHeight;
        this.debug('element', this.element);
        const zoom = d3.behavior.zoom()
            .on("zoom", redraw);

        const svg = d3.select(this.element)
            .append("svg")
            .attr("width", width)
            .attr("height", height)
            .attr('class', 'architectureVisualisation')
            .call(zoom)
            .append("g");
        const root = svg.append('g').attr('class', 'root');

        this.svg = svg;
        this.layouter = cola.d3adaptor()
            .linkDistance(80)
            .avoidOverlaps(true)
            .handleDisconnected(false)
            .size([width, height]);
        // group shizzle

        this.debug('element', this.element);

        function redraw() {
            svg.attr("transform", "translate(" + d3.event.translate + ") scale(" + d3.event.scale + ")");
        }

        const graph = this.get('graph');

        const nodes = graph.nodes;
        const links = graph.links;
        const groups = graph.groups;
        this.layouter
            .nodes(nodes)
            .links(links)
            .groups(groups)
            .start(100, 0, 50, 50);

        // nodes
        const nodeData = root.selectAll('.node')
            .data(nodes);
        const groupData = root.selectAll('.group')
            .data(groups);
        const nodeEnter = nodeData.enter()
            .append('g');

        nodeData
            .attr('class', 'node leaf')
            .attr('width', 10)
            .attr('height', 10)
            .attr('x', 0)
            .attr('y', 0);

        const atoms = nodeEnter.append('rect');

        nodeEnter.append('title')
            .text(d => d.id);

        nodeEnter.append('text')
            .attr('x', (d) => d.children? 0 : 2.5)
            .attr('y', 5)
            .text((d) => _.get(d, 'labels.0.text', d.id))
            .attr('font-size', '4px')
            .attr('font-size', '4px');

        nodeData.exit()
            .remove();

        // edges
        // FIXME does not handle updates (neither enter() nor exit())
        const linkData = root.selectAll('.link')
            .data(links, p => p.id);
        const link = linkData.enter()
            .append('path')
            .attr('class', 'link')
            .attr('d', 'M0 0');


        linkData.exit()
            .remove();

        // apply edge routes
        // linkData.transition().attr('d', (d) => {
        //     log('test', this.layouter);
        //   let path = '';
        //   path += 'M' + d.sourcePoint.x + ' ' + d.sourcePoint.y + ' ';
        //     (d.bendPoints || []).forEach(bp => path += 'L' + bp.x + ' ' + bp.y + ' ');
        //   path += 'L' + d.targetPoint.x + ' ' + d.targetPoint.y + ' ';
        //   return path;
        // });

        // apply nodeEnter positions
        nodeData.transition()
          .attr('transform', (d) => 'translate(' + (d.x || 0) + ' ' + (d.y || 0) + ')');

        atoms.transition()
          .attr('width', (d) => d.width)
          .attr('height', (d) => d.height);
        // this.layouter.on('finish', onFinish);
        // this.renderGraph();
    }
});
