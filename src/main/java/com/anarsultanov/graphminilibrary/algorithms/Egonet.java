package com.anarsultanov.graphminilibrary.algorithms;

import com.anarsultanov.graphminilibrary.graph.BasicGraph;
import com.anarsultanov.graphminilibrary.graph.Graph;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * An algorithm which computes the EgoNet of the specified node.
 */
public class Egonet {

    /**
     * Apply the algorithm to the graph and get the result
     * @param graph the graph to which to apply the algorithm
     * @param center the node
     * @param <T> the graph nodes type
     * @return CompletableFuture with the result
     */
    public static <T> CompletableFuture<Graph<T>> compute(Graph<T> graph, T center) {
        if (!graph.hasNode(center)) {
            throw new IllegalArgumentException("There is no specified node in the graph");
        }
        return CompletableFuture.supplyAsync(() -> doCompute(graph, center));
    }

    private static <T> Graph<T> doCompute(Graph<T> graph, T center) {
        Graph<T> egonet = new BasicGraph<>();
        egonet.addNode(center);
        Set<T> neighbors = graph.outEdges(center);
        neighbors.forEach(egonet::addNode);

        egonet.getAdjacencyListMap().keySet()
                .forEach(k -> graph.getAdjacencyListMap().get(k).forEach(v -> {
                    if (egonet.hasNode(v))
                        egonet.addEdge(k, v);
                }));
        return egonet;
    }
}
