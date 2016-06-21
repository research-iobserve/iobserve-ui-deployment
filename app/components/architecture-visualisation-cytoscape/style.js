/**
    you should set syntax highlighting to CSS
    this is a .js file, because we can then use static imports which ember-cli can properly build.
    Notice that this is only pseudo-css for cytoscape. See the cytoscape.js docs for more info.
*/

export default function(variables){
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
    text-valign: top;
    text-halign: center;
}

[type="nodeGroup"] {
    events: no; /* disable click, drag etc as this is just a frame */
}

[type="node"] {
    background-color: ${variables.nodeColor};
    color: ${variables.nodeTextColor};
    font-weight: bold;

}

[type="serviceInstance"] {
    background-color: ${variables.serviceColor};
    color: ${variables.serviceTextColor};
    font-weight: bold;
    shadow-blur: 0;
    shadow-color: #000;
    shadow-offset-x: 2px;
    shadow-offset-y: 2px;
    shadow-opacity: 0.5;
}
[type="communicationInstance"] {
    color: #F4EFDC;
    line-color: ${variables.arrowBorderColor};
    target-arrow-color: ${variables.arrowColor};
}

[type="nodeGroup"] {
    color: ${variables.nodeGroupTextColor};
    background-color: ${variables.nodeGroupColor};
    border-style: dotted;
    border-color: ${variables.borderColor};
    border-width: 2px;
    font-weight: bold;
}

edge {
    label: data(label);
    color: ${variables.arrowLabelColor};
    font-weight: bold;
    target-arrow-shape: triangle-backcurve;
    curve-style: bezier; /* supports arrows */
    width: 2px;
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
