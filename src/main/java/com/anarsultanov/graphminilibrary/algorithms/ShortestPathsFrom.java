package com.anarsultanov.graphminilibrary.algorithms;

import com.anarsultanov.graphminilibrary.graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * An algorithm which computes the shortest path from specified node to all other nodes.
 */
public class ShortestPathsFrom {

    /**
     * Apply the algorithm to the graph and get the result
     * @param graph the graph to which to apply the algorithm
     * @param from the starting node
     * @param <T> the graph nodes type
     * @return CompletableFuture with the result
     */
    public static <T> CompletableFuture<List<List<T>>> compute(Graph<T> graph, T from) {
        if (!graph.hasNode(from)) {
            throw new IllegalArgumentException("Start node " + from + " does not exist");
        }
        return CompletableFuture.supplyAsync(() -> doCompute(graph, from));
    }

    private static <T> List<List<T>> doCompute(Graph<T> graph, T from) {
        List<List<T>> pathsList = new ArrayList<>();
        Set<T> settledNodes = new HashSet<>();
        Map<T, List<T>> unsettledNodes = new HashMap<>();
        unsettledNodes.put(from, new LinkedList<>());

        while (!unsettledNodes.isEmpty()) {
            Entry<T, List<T>> current = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(current.getKey());

            if (from != current.getKey()) {
                List<T> pathTemp = new ArrayList<>(current.getValue());
                pathTemp.add(current.getKey());
                pathsList.add(pathTemp);
            }

            for (T next : graph.getAdjacencyListMap().get(current.getKey())) {
                if (!settledNodes.contains(next)) {
                    if (unsettledNodes.containsKey(next)) {
                        List<T> path = new LinkedList<>(current.getValue());
                        path.add(current.getKey());
                        int oldDist = unsettledNodes.get(next).size();
                        int newDist = path.size();
                        if (oldDist > newDist) {
                            unsettledNodes.put(next, path);
                        }
                    } else {
                        List<T> path = new LinkedList<>(current.getValue());
                        path.add(current.getKey());
                        unsettledNodes.put(next, path);
                    }
                }
            }
            settledNodes.add(current.getKey());
        }
        return pathsList;
    }

    private static <T> Entry<T, List<T>> getLowestDistanceNode(Map<T, List<T>> unsettledNodes) {
        Entry<T, List<T>> lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Entry<T, List<T>> node : unsettledNodes.entrySet()) {
            int nodeDistance = node.getValue().size();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
}
