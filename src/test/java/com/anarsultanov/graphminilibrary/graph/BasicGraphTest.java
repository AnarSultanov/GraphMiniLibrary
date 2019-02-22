package com.anarsultanov.graphminilibrary.graph;

import com.anarsultanov.graphminilibrary.utilities.GraphLoader;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BasicGraphTest {

    private Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = new BasicGraph<>();
        GraphLoader.loadGraph(graph, "data/test_graph");
    }

    @Test
    public void getAdjacencyListMap() {
        assertEquals(12, graph.getAdjacencyListMap().size());
    }

    @Test
    public void getNodesNumber() {
        assertEquals(12, graph.getNodesNumber());
    }

    @Test
    public void getEdgesNumber() {
        assertEquals(38, graph.getEdgesNumber());
    }

    @Test
    public void addNode() {
        graph.addNode(20);
        assertTrue(graph.hasNode(20));
    }

    @Test
    public void removeNode() {
        graph.removeNode(1);
        assertFalse(graph.hasNode(1));
    }

    @Test
    public void outEdges() {
        assertTrue(graph.outEdges(2).containsAll(List.of(1, 3, 4)));
    }

    @Test
    public void inEdges() {
        assertTrue(graph.inEdges(2).containsAll(List.of(1, 3, 4)));
    }

    @Test
    public void addEdge() {
        graph.addEdge(25, 31);
        assertTrue(graph.hasEdge(25, 31));
    }

    @Test
    public void removeEdge() {
        graph.removeEdge(5, 6);
        assertFalse(graph.hasEdge(5, 6));
    }

    @Test
    public void hasNode() {
        assertTrue(graph.hasNode(1));
    }

    @Test
    public void hasEdge() {
        assertTrue(graph.hasEdge(5, 6));
    }

    @Test
    public void clear() {
        graph.clear();
        assertEquals(0, graph.getEdgesNumber());
        assertEquals(0, graph.getNodesNumber());
    }
}