// you should set syntax highlighting to CSS
export default `


node {
    content: data(name);
    text-valign: center;
    text-halign: center;
}

$node > node {
    padding-top: 10px;
    padding-left: 10px;
    padding-bottom: 10px;
    padding-right: 10px;
    text-valign: top;
    text-halign: center;
    background-color: #bbb;
}

edge {
    target-arrow-shape: triangle;
}

:selected {
    background-color: black;
    line-color: black;
    target-arrow-color: black;
    source-arrow-color: black;
}


`;