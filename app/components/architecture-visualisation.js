import Ember from 'ember';
import d3 from 'd3';
import klay from 'npm:klayjs-d3';
import _ from 'npm:lodash';

export default Ember.Component.extend({
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
            // .call(zoom)
            .append("g");
        const root = svg.append("g");

        this.layouter = klay.d3kgraph()
            .size([width, height])
            .transformGroup(root)
            .options({
                edgeRouting: "ORTHOGONAL"
            });
        // group shizzle

        this.debug('element', this.element);

        function redraw() {
            svg.attr("transform", "translate(" + d3.event.translate + ") scale(" + d3.event.scale + ")");
        }

        const onFinish = (layout) => {
            log('loaded layout', layout);
            const nodes = this.layouter.nodes();
            const links = this.layouter.links(nodes);

            const nodeData = root.selectAll('.node')
                .data(nodes, p => p.id);
            const node = nodeData.enter()
                .append('g')
                .attr('class', d => d.children? 'node compound' : 'node leaf');

            const atoms = node.append('rect')
                .attr('width', 10)
                .attr('height', 10)
                .attr('x', 0)
                .attr('y', 0);

            node.append('title')
                .text(d => d.id);

            node.append('text')
                .attr('x', (d) => d.children? 0 : 2.5)
                .attr('y', 5)
                .text((d) => _.get(d, 'labels.0.text', d.id))
                .attr('font-size', '4px')
                .attr('font-size', '4px');


            const linkData = root.selectAll('.link')
                .data(links, p => p.id);
            const link = linkData.enter()
                .append('path')
                .attr('class', 'link')
                .attr('d', 'M0 0');

            // apply edge routes
            link.transition().attr('d', (d) => {
              let path = '';
              path += 'M' + d.sourcePoint.x + ' ' + d.sourcePoint.y + ' ';
                (d.bendPoints || []).forEach(bp => path += 'L' + bp.x + ' ' + bp.y + ' ');
              path += 'L' + d.targetPoint.x + ' ' + d.targetPoint.y + ' ';
              return path;
            });

            // apply node positions
            node.transition()
              .attr('transform', (d) => 'translate(' + (d.x || 0) + ' ' + (d.y || 0) + ')');

            atoms.transition()
              .attr('width', (d) => d.width)
              .attr('height', (d) => d.height);
        };
        this.layouter.on('finish', onFinish);
        this.layouter.kgraph(this.get('graph'));
    }
});


