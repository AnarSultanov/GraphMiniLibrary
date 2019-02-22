package com.anarsultanov.graphminilibrary.algorithms;

import com.anarsultanov.graphminilibrary.graph.BasicGraph;
import com.anarsultanov.graphminilibrary.graph.Graph;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;

/**
 * An algorithm for detecting communities by removing connections with the largest betweenness.
 */
public class CommunitySeparation {

    /**
     * Apply the algorithm to the graph and get the result
     * @param graph the graph to which to apply the algorithm
     * @param connectionsToRemove number of connections to remove
     * @param <T> the graph nodes type
     * @return CompletableFuture with the result
     */
    public static <T> CompletableFuture<Result<T>> compute(Graph<T> graph, int connectionsToRemove) {
        if (connectionsToRemove > graph.getEdgesNumber()) {
            throw new IllegalArgumentException("The number of edges to remove exceeds the total the total number of edges!");
        }
        return CompletableFuture.supplyAsync(() -> doCompute(graph, connectionsToRemove));
    }

    private static <T> Result<T> doCompute(Graph<T> graph, int connectionsToRemove) {
        Graph<T> resultingGraph = new BasicGraph<>();
        Map<T, T> removedEdges = new HashMap<>();
        copyAdjacencyListMap(graph, resultingGraph);
        NavigableSet<Entry<Entry<T, T>, Integer>> queue = new TreeSet<>(Comparator.comparing(Entry::getValue));
        for (int i = 0; i < connectionsToRemove; i++) {
            queue.clear();
            queue.addAll(getBetweennes(resultingGraph).entrySet());

            Entry<Entry<T, T>, Integer> last = queue.pollLast();
            assert last != null;
            T v1 = last.getKey().getKey();
            T v2 = last.getKey().getValue();

            resultingGraph.removeEdge(v1, v2);
            removedEdges.put(v1, v2);
            if (resultingGraph.hasEdge(v2, v1)) {
                resultingGraph.removeEdge(v2, v1);
                removedEdges.put(v2, v1);
            }
        }
        return new Result<>(resultingGraph, removedEdges);
    }

    private static <T> void copyAdjacencyListMap(Graph<T> source, Graph<T> goal) {
        for (T from : source.getAdjacencyListMap().keySet()) {
            for (T to : source.getAdjacencyListMap().get(from)) {
                goal.addEdge(from, to);
            }
        }
    }

    private static <T> Map<Entry<T, T>, Integer> getBetweennes(Graph<T> graph) {

        Map<Entry<T, T>, Integer> betweennesMap = new HashMap<>();

        for (T start : graph.getAdjacencyListMap().keySet()) {
            HashMap<T, T> parentMap = new HashMap<>();
            Queue<T> toExplore = new LinkedList<>();
            HashSet<T> visited = new HashSet<>();

            toExplore.add(start);
            T next;

            while (!toExplore.isEmpty()) {
                next = toExplore.remove();
                Set<T> neighbors = graph.getAdjacencyListMap().get(next);
                for (T n : neighbors) {
                    if (!visited.contains(n)) {
                        visited.add(n);
                        parentMap.put(n, next);
                        toExplore.add(n);
                    }
                }
            }

            for (T s : parentMap.keySet()) {
                T current = s;
                while (!current.equals(start)) {
                    Entry<T, T> edge = new AbstractMap.SimpleEntry<>(parentMap.get(current), current);
                    if (betweennesMap.containsKey(edge)) {
                        int betweennes = betweennesMap.get(edge) + 1;
                        betweennesMap.replace(edge, betweennes);
                    } else {
                        betweennesMap.put(edge, 1);
                    }
                    current = parentMap.get(current);
                }
            }
        }
        return betweennesMap;
    }

    /**
     * The result of the algorithm.
     */
    public static class Result<T> {
        private Graph<T> resultingGraph;
        private Map<T, T> removedEdges;

        private Result(Graph<T> resultingGraph, Map<T, T> removedEdges) {
            this.resultingGraph = resultingGraph;
            this.removedEdges = removedEdges;
        }

        /**
         * Get the resulting graph
         * @return the resulting graph
         */
        public Graph<T> getResultingGraph() {
            return resultingGraph;
        }

        /**
         * Get the removed edges
         * @return the removed edges
         */
        public Map<T, T> getRemovedEdges() {
            return removedEdges;
        }
    }
}
