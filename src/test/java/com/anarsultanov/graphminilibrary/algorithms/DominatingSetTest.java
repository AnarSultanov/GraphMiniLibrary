package com.anarsultanov.graphminilibrary.algorithms;

import com.anarsultanov.graphminilibrary.graph.BasicGraph;
import com.anarsultanov.graphminilibrary.graph.Graph;
import com.anarsultanov.graphminilibrary.utilities.GraphLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

public class DominatingSetTest {

    private Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = new BasicGraph<>();
        GraphLoader.loadGraph(graph, "data/test_graph");
    }

    @Test
    public void testCompute() throws ExecutionException, InterruptedException {
        Set<Integer> set = DominatingSet.compute(graph).get();
        assertTrue(set.contains(1));
        assertTrue(set.contains(4));
        assertTrue(set.contains(7));
        assertTrue(set.contains(12));
    }
}