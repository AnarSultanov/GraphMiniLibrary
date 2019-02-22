package com.anarsultanov.graphminilibrary.utilities;

import com.anarsultanov.graphminilibrary.graph.BasicGraph;
import com.anarsultanov.graphminilibrary.graph.Graph;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GraphViewerTest {

    private Graph<Integer> graph;

    @Before
    public void setUp() {
        graph = new BasicGraph<>();
        GraphLoader.loadGraph(graph, "data/test_graph");
    }

    @Test
    public void displayGraph() {
        GraphViewer.displayGraph(graph);
    }
}