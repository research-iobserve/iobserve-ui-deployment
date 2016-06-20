/**
    you should set syntax highlighting to CSS
    this is a .js file, because we can then use static imports which ember-cli can properly build.
    Notice that this is only pseudo-css for cytoscape. See the cytoscape.js docs for more info.
*/

/*https://color.adobe.com/anggo-djago-color-theme-2691053/*/
const arrowBorderColor = '#002A4A';
const arrowColor = arrowBorderColor;


const nodeTextColor = '#002A4A';
const nodeColor = '#17607D';

const serviceColor = '#ED8910';
const serviceTextColor = '#FFFEED';/* '#FFF1CE';*/

const nodeGroupTextColor = nodeTextColor;
const nodeGroupColor = 'white';
const borderColor = '#D64700';


export default `


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

[type="node"] {
    background-color: ${nodeColor};
    color: ${nodeTextColor};
    font-weight: bold;

}

[type="serviceInstance"] {
    background-color: ${serviceColor};
    color: ${serviceTextColor};
    font-weight: bold;
}
[type="communicationInstance"] {
    color: #F4EFDC;
    line-color: ${arrowBorderColor};
    target-arrow-color: ${arrowColor};
}

[type="nodeGroup"] {
    color: ${nodeGroupTextColor};
    background-color: ${nodeGroupColor};
    border-style: dotted;
    border-color: ${borderColor};
    border-width: 2px;
    font-weight: bold;
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
