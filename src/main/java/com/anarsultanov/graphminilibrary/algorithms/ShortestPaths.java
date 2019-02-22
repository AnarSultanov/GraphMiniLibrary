package com.anarsultanov.graphminilibrary.algorithms;

import com.anarsultanov.graphminilibrary.graph.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * An algorithm which computes the shortest path from all nodes to all other nodes.
 */
public class ShortestPaths {

    /**
     * Apply the algorithm to the graph and get the result
     * @param graph the graph to which to apply the algorithm
     * @param <T> the graph nodes type
     * @return CompletableFuture with the result
     */
    public static <T> CompletableFuture<List<List<T>>> compute(Graph<T> graph) {
        List<CompletableFuture<List<List<T>>>> futures = graph.getAdjacencyListMap().keySet().stream()
                .map(n -> ShortestPathsFrom.compute(graph, n))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v ->
                        futures.stream()
                                .map(CompletableFuture::join)
                                .flatMap(Collection::stream)
                                .collect(Collectors.toList()));
    }
}
