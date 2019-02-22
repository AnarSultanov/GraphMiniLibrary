package com.anarsultanov.graphminilibrary.algorithms;

import com.anarsultanov.graphminilibrary.graph.Graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * An algorithm which computes the shortest path from one node to another.
 */
public class ShortestPathFromTo {

    /**
     * Apply the algorithm to the graph and get the result
     * @param graph the graph to which to apply the algorithm
     * @param from the starting node
     * @param to the end point
     * @param <T> the graph nodes type
     * @return CompletableFuture with the result
     */
    public static <T> CompletableFuture<List<T>> compute(Graph<T> graph, T from, T to) {
        if (!graph.hasNode(from)) {
            throw new IllegalArgumentException("Start node " + from + " does not exist");
        }
        if (!graph.hasNode(to)) {
            throw new IllegalArgumentException("End node " + from + " does not exist");
        }
        return CompletableFuture.supplyAsync(() -> doCompute(graph, from, to));
    }

    private static <T> List<T> doCompute(Graph<T> graph, T from, T to) {

        HashMap<T, T> parentMap = new HashMap<>();
        Queue<T> toExplore = new LinkedList<>();
        HashSet<T> visited = new HashSet<>();

        toExplore.add(from);
        T next = null;

        while (!toExplore.isEmpty()) {
            next = toExplore.remove();

            if (next.equals(to))
                break;

            Set<T> neighbors = graph.getAdjacencyListMap().get(next);
            for (T n : neighbors) {
                if (!visited.contains(n)) {
                    visited.add(n);
                    parentMap.put(n, next);
                    toExplore.add(n);
                }
            }
        }

        assert next != null;
        if (!next.equals(to)) {
            return Collections.emptyList();
        }
        return reconstructPath(parentMap, from, to);
    }

    private static <T> List<T> reconstructPath(HashMap<T, T> parentMap, T from, T to) {
        LinkedList<T> path = new LinkedList<>();
        T current = to;

        while (!current.equals(from)) {
            path.addFirst(current);
            current = parentMap.get(current);
        }
        path.addFirst(from);
        return path;
    }
}
