package com.anarsultanov.graphminilibrary.algorithms;

import com.anarsultanov.graphminilibrary.graph.Graph;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;

/**
 * An algorithm which computes the dominating set.
 */
public class DominatingSet {

    /**
     * Apply the algorithm to the graph and get the result
     * @param graph the graph to which to apply the algorithm
     * @param <T> the graph nodes type
     * @return CompletableFuture with the result
     */
    public static <T> CompletableFuture<Set<T>> compute(Graph<T> graph) {
        return CompletableFuture.supplyAsync(() -> doCompute(graph));
    }

    @SuppressWarnings("ComparatorMethodParameterNotUsed")
    private static <T> Set<T> doCompute(Graph<T> graph) {
        Set<T> dominatingSet = new HashSet<>();
        Set<T> marked = new HashSet<>();
        NavigableSet<Entry<T, Integer>> queue = new TreeSet<>((arg0, arg1) -> {
            int res = arg0.getValue().compareTo(arg1.getValue());
            return res != 0 ? res : 1;
        });
        graph.getAdjacencyListMap().forEach((k, v) -> queue.add(new AbstractMap.SimpleEntry<>(k, v.size())));

        while (!queue.isEmpty()) {
            Entry<T, Integer> e = queue.pollLast();
            assert e != null;
            T v = e.getKey();
            if (!dominatingSet.contains(v) && !marked.contains(v)) {
                dominatingSet.add(v);
                marked.addAll(graph.getAdjacencyListMap().get(v));
            }
        }
        return dominatingSet;
    }
}
