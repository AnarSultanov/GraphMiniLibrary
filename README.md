# Graph Mini Library
Small library for working with graphs.

### Usage
#### Create, load and save the graph.
```java
Graph<String> graph = new BasicGraph<>();
GraphLoader.loadGraph(graph, "graph.txt");
GraphLoader.saveGraph(graph, "graph.txt");
```

#### Add, remove and check nodes and edges.
Note: Non-existent nodes are created automatically when edges are added.
```java
graph.addNode("A");
graph.addEdge("B", "C");
graph.hasNode("A");
graph.hasEdge("B", "C");
graph.removeNode("A");
graph.removeEdge("B", "C");
```
#### Use algorithms and get the result.
Note: 
- Initial data og the graph is not changed during calculations.
- All algorithms are calculated asynchronously; therefore, CompletableFuture is returned.
```java
Graph<Integer> graph = new BasicGraph<>();
CompletableFuture<List<Integer>> future = ShortestPathFromTo.compute(graph, 1, 7);
List<Integer> result = future.get();
```

#### Display the graph in the applet
```java
GraphViewer.displayGraph(graph);
```

### API reference
[Javadoc](https://anarsultanov.github.io/GraphMiniLibrary/)

### About
This library is my capstone project for Object Oriented Java Programming specialisation (University of California, San Diego on Coursera). 
I will not develop it further, since there are many great libraries for working with graphs, and I see no reason to waste time on another one.

### License
This project is licensed under the terms of the MIT license.
