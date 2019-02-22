package com.anarsultanov.graphminilibrary.algorithms;

import com.anarsultanov.graphminilibrary.graph.BasicGraph;
import com.anarsultanov.graphminilibrary.graph.Graph;
import com.anarsultanov.graphminilibrary.utilities.GraphLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EgonetTest {

    private Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = new BasicGraph<>();
        GraphLoader.loadGraph(graph, "data/test_graph");
    }

    @Test
    public void testCompute() throws ExecutionException, InterruptedException {
        Graph<Integer> egonet = Egonet.compute(graph, 1).get();
        assertEquals(3, egonet.getNodesNumber());
        assertEquals(4, egonet.getEdgesNumber());
        assertTrue(egonet.hasEdge(1, 2));
        assertTrue(egonet.hasEdge(1, 5));
        assertTrue(egonet.hasEdge(2, 1));
        assertTrue(egonet.hasEdge(5, 1));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testMissingNode() {
        Egonet.compute(graph, 25);
    }
}