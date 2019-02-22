package com.anarsultanov.graphminilibrary.algorithms;

import com.anarsultanov.graphminilibrary.graph.BasicGraph;
import com.anarsultanov.graphminilibrary.graph.Graph;
import com.anarsultanov.graphminilibrary.utilities.GraphLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

public class ShortestPathFromToTest {

    private Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = new BasicGraph<>();
        GraphLoader.loadGraph(graph, "data/test_graph");
    }

    @Test
    public void testCompute() throws ExecutionException, InterruptedException {
        List<Integer> list = ShortestPathFromTo.compute(graph, 1, 7).get();
        assertTrue(list.containsAll(List.of(1, 5, 6, 7)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingFromNode() {
        ShortestPathFromTo.compute(graph, 0, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingToNode() {
        ShortestPathFromTo.compute(graph, 1, 15);
    }
}