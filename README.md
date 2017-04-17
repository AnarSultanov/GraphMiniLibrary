# Graph Mini Library
Small library for working with graphs.

### Usage
Creating, loading and saving of the graph.
```java
Graph graph = new BasicGraph();
GraphLoader.loadGraph(graph, "graph.txt");
GraphLoader.saveGraph(graph, "graph.txt");
```
Adding, removing and checking for the presence of nodes and edges.
note: When adding edges, if the nodes do not exist, they are created automatically.
```java
graph.addNode("A");
graph.addEdge("B", "C");
graph.hasNode("A");
graph.hasEdge("B", "C");
graph.removeNode("A");
graph.removeEdge("B", "C");
```
Work with algorithms and getting the result of computing. 
note: Start to compute requires the initialization of the algorithm and setting of additional sources for some algorithms.
```java
ShortestPathFromTo spft = new ShortestPathFromTo();
spft.init(graph);
spft.setSource("1", "7");
spft.compute();
spft.getPath();
spft.clear();

DominatingSet ds = new DominatingSet();
ds.init(graph);
ds.compute();
ds.getDominatingSet();
ds.clear();
```

Graphical display of the graph in the applet with the ability to drag nodes.
```java
GraphViewer.displayGraph(graph);
```

### API reference
Coming soon.

### About
This library is my capstone project for Object Oriented Java Programming specialisation (University of California, San Diego on Coursera). Perhaps the project will not develop further, since there are many libraries for working with graphs and I do not see any reason to waste time on one more.

### License
This project is licensed under the terms of the MIT license.