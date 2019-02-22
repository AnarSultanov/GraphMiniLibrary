package com.anarsultanov.graphminilibrary.algorithms;

import com.anarsultanov.graphminilibrary.graph.BasicGraph;
import com.anarsultanov.graphminilibrary.graph.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.CompletableFuture;

/**
 * An algorithm which computes strongly connected components.
 */
public class StronglyConnectedComponents {

    /**
     * Apply the algorithm to the graph and get the result
     * @param graph the graph to which to apply the algorithm
     * @param <T> the graph nodes type
     * @return CompletableFuture with the result
     */
    public static <T> CompletableFuture<List<Graph<T>>> compute(Graph<T> graph) {
        return CompletableFuture.supplyAsync(() -> doCompute(graph));
    }

    private static <T> List<Graph<T>> doCompute(Graph<T> graph) {
        Graph<T> reverseGraph = reverseGraph(graph);
        List<Graph<T>> sccGraphs = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        Stack<T> stack = new Stack<>();

        graph.getAdjacencyListMap().keySet().forEach(k -> {
            if (!visited.contains(k))
                sccFirstMethod(graph, k, visited, stack);
        });

        visited.clear();

        while (!stack.isEmpty()) {
            T v = stack.pop();
            HashSet<T> sccVertices = new HashSet<>();
            if (!visited.contains(v)) {
                sccSecondMethod(reverseGraph, v, visited, sccVertices);
                BasicGraph<T> sccGraph = new BasicGraph<>();
                sccVertices.forEach(sccGraph::addNode);
                sccGraph.getAdjacencyListMap().keySet()
                        .forEach(key -> graph.getAdjacencyListMap().get(key)
                                .forEach(k -> {
                                    if (sccGraph.hasNode(k))
                                        sccGraph.addEdge(key, k);
                                }));
                sccGraphs.add(sccGraph);
            }
        }
        return sccGraphs;
    }

    private static <T> void sccFirstMethod(Graph<T> graph, T node, Set<T> visited, Stack<T> stack) {
        visited.add(node);
        graph.getAdjacencyListMap().get(node).forEach(n -> {
            if (!visited.contains(n))
                sccFirstMethod(graph, n, visited, stack);
        });
        stack.push(node);
    }

    private static <T> void sccSecondMethod(Graph<T> graph, T node, Set<T> visited, HashSet<T> sccVertices) {
        visited.add(node);
        sccVertices.add(node);
        if (!graph.hasNode(node))
            return;
        graph.getAdjacencyListMap().get(node).forEach(neighbor -> {
            if (!visited.contains(neighbor)) {
                sccSecondMethod(graph, neighbor, visited, sccVertices);
            }
        });
    }

    private static <T> Graph<T> reverseGraph(Graph<T> graph) {
        Graph<T> reverseGraph = new BasicGraph<>();
        graph.getAdjacencyListMap().keySet()
                .forEach(k -> graph.getAdjacencyListMap().get(k)
                        .forEach(l -> reverseGraph.addEdge(l, k)));
        return reverseGraph;
    }
}
