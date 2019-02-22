package com.anarsultanov.graphminilibrary.utilities;

import com.anarsultanov.graphminilibrary.graph.BasicGraph;
import com.anarsultanov.graphminilibrary.graph.Graph;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class GraphLoaderTest {

    private Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = new BasicGraph<>();
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
    }

    @Test
    public void testGraphLoader() {
        GraphLoader.saveGraph(graph, "graph_loader_test.txt");
        Graph<Integer> loaded = new BasicGraph<>();
        GraphLoader.loadGraph(loaded, "graph_loader_test.txt");
        assertEquals(graph, loaded);
    }

    @AfterClass
    public static void cleanup() {
		File file = new File("graph_loader_test.txt");
		file.delete();
    }

}