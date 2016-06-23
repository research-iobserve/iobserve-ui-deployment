/**
    you should set syntax highlighting to CSS
    this is a .js file, because we can then use static imports which ember-cli can properly build.
    Notice that this is only pseudo-css for cytoscape. See the cytoscape.js docs for more info.
*/

export default function(theme){
    return `


* {
    font-size: 10pt;
}

node { /* all nodes */
    content: data(label);
    shape: roundrectangle;
    text-valign: center;
    text-halign: center;
    width: 100px;
    height: 60px;
    text-wrap: wrap;
    text-max-width: 90px;
}

$node > node { /* compounds. "Nodes" in meta model. $ selects the parent node that has a node instead of the node (as css would) */
    padding-top: 10px;
    padding-left: 10px;
    padding-bottom: 10px;
    padding-right: 10px;
    border-width: 2px;
    text-valign: top;
    text-halign: center;
}

[type="nodeGroup"] {
    events: no; /* disable click, drag etc as this is just a frame */
}

[type="node"] {
    background-color: ${theme.nodeColor};
    color: ${theme.nodeTextColor};
    border-color: ${theme.nodeBorderColor};
    font-weight: bold;
    z-index: 100;

}

[type="serviceInstance"] {
    background-color: ${theme.serviceColor};
    color: ${theme.serviceTextColor};
    border-color: ${theme.serviceBorderColor};
    border-style: solid;
    border-width: 1px;
    font-weight: bold;
    shadow-blur: 0;
    shadow-color: #000;
    shadow-offset-x: 2px;
    shadow-offset-y: 2px;
    shadow-opacity: 0.5;
}

[type="communicationInstance"] {
    color: ${theme.arrowColor};
    line-color: ${theme.arrowColor};
    target-arrow-color: ${theme.arrowColor};
    text-background-color: ${theme.arrowLineColor};
    text-outline-color:${theme.arrowLineColor};
    text-background-opacity: 1;
    text-background-shape: roundrectangle;
    font-size: 12pt;
}

[type="nodeGroup"] {
    color: ${theme.nodeGroupTextColor};
    background-color: ${theme.nodeGroupColor};
    border-style: dotted;
    border-color: ${theme.nodeGroupBorderColor};
    border-width: 2px;
    font-weight: bold;
    z-index: 100;
}

edge {
    label: data(label);
    color: ${theme.arrowLabelColor};
    font-weight: bold;
    target-arrow-shape: triangle-backcurve;
    curve-style: bezier;/* supports arrow heads*/
    edge-distances: node-position;
    z-index: 0;


    width: data(workload);

}

:selected {
    background-color: black;
    line-color: black;
    target-arrow-color: black;
    source-arrow-color: red;
}

:touch {
    border-width: 2px;
}


`; /* js string end */
}
