/**
    you should set syntax highlighting to CSS
    this is a .js file, because we can then use static imports which ember-cli can properly build.
    Notice that this is only pseudo-css for cytoscape. See the cytoscape.js docs for more info.
*/
export default `

* {
    font-size: 10pt;
}

node { /* all nodes */
    content: data(label);
    shape: roundrectangle;
    text-valign: center;
    text-halign: center;
}

$node > node { /* compounds. "Nodes" in meta model. $ selects the parent node that has a node instead of the node (as css would) */
    padding-top: 10px;
    padding-left: 10px;
    padding-bottom: 10px;
    padding-right: 10px;
    text-valign: top;
    text-halign: center;
}

[type="node"] {
    background-color: #CCC;
}

[type="nodeGroup"] {
    background-color: white;
    border-style: dotted;
    border-color: #999;
    border-width: 2px;
}

edge {
    label: data(label);
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