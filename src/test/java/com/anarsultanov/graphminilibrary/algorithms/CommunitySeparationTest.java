package com.anarsultanov.graphminilibrary.algorithms;

import com.anarsultanov.graphminilibrary.graph.BasicGraph;
import com.anarsultanov.graphminilibrary.graph.Graph;
import com.anarsultanov.graphminilibrary.utilities.GraphLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class CommunitySeparationTest {

    private Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = new BasicGraph<>();
        GraphLoader.loadGraph(graph, "data/test_graph");
	}

    @Test
    public void testCompute() throws ExecutionException, InterruptedException {
        CommunitySeparation.Result<Integer> result = CommunitySeparation.compute(graph, 1).get();
        assertEquals(2, result.getRemovedEdges().size());
        assertEquals(6, result.getRemovedEdges().get(7).longValue());
        assertEquals(7, result.getRemovedEdges().get(6).longValue());
        assertEquals(36, result.getResultingGraph().getEdgesNumber());
        assertEquals(12, result.getResultingGraph().getNodesNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooManyEdges() {
        CommunitySeparation.compute(graph, 100);
    }
}