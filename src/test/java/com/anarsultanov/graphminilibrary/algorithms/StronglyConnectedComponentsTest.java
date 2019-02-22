package com.anarsultanov.graphminilibrary.algorithms;

import com.anarsultanov.graphminilibrary.graph.BasicGraph;
import com.anarsultanov.graphminilibrary.graph.Graph;
import com.anarsultanov.graphminilibrary.utilities.GraphLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class StronglyConnectedComponentsTest {

    private Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = new BasicGraph<>();
        GraphLoader.loadGraph(graph, "data/test_graph");
    }

    @Test
    public void testCompute() throws ExecutionException, InterruptedException {
        List<Graph<Integer>> graphs = StronglyConnectedComponents.compute(graph).get();
        assertEquals(1, graphs.size());
    }
}