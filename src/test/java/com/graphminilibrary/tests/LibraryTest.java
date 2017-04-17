package com.graphminilibrary.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.graphminilibrary.algorithms.*;
import com.graphminilibrary.graph.*;
import com.graphminilibrary.utilities.*;

/**
 * The Class LibraryTest.
 */
public class LibraryTest {
	
	/** The graph. */
	static Graph graph;
	
	/** The community separation. */
	static CommunitySeparation communitySeparation;
	
	/** The dominating set. */
	static DominatingSet dominatingSet;
	
	/** The egonet. */
	static Egonet egonet;
	
	/** The scc. */
	static SCC scc;
	
	/** The shortest path from to. */
	static ShortestPathFromTo shortestPathFromTo;
	
	/** The shortest paths. */
	static ShortestPaths shortestPaths;
	
	/** The shortest paths from. */
	static ShortestPathsFrom shortestPathsFrom;
	
	/**
	 * Sets the up class.
	 */
	@BeforeClass
    public static void setUpClass() {
		
        graph = new BasicGraph();
        communitySeparation = new CommunitySeparation();
    	dominatingSet = new DominatingSet();
    	egonet = new Egonet();
    	scc = new SCC();
    	shortestPathFromTo = new ShortestPathFromTo();
    	shortestPaths = new ShortestPaths();
    	shortestPathsFrom = new ShortestPathsFrom();
    }	

	/**
	 * Sets the up.
	 */
	@Before
    public void setUp() {
		GraphLoader.loadGraph(graph, "data/test_graph.txt");
	}
	
	/**
	 * Graph test.
	 */
	@Test
	public void graphTest() {
		assertEquals(12, graph.getNodesNumber());
		assertEquals(38, graph.getEdgesNumber());
		
		assertTrue(graph.hasNode("2"));
		assertFalse(graph.hasNode("15"));
		
		assertTrue(graph.hasEdge("5", "6"));
		assertFalse(graph.hasEdge("5", "8"));
		
		assertTrue(graph.inEdges("1").contains("5"));
		assertTrue(graph.outEdges("1").contains("2"));
		
		graph.clear();
		assertEquals(0, graph.getNodesNumber());
		assertEquals(0, graph.getEdgesNumber());
	}

	/**
	 * Community separation test.
	 */
	@Test
	public void communitySeparationTest() {
		try {
			communitySeparation.compute();
			fail("Must throw AlgorithmNotInitializedException");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		communitySeparation.init(graph);
		communitySeparation.setSource(1);
		communitySeparation.compute();
		communitySeparation.getRemovedEdges().forEach(edge -> {
			assertTrue(edge.contains("6"));
			assertTrue(edge.contains("7"));
		});
		assertEquals(2, communitySeparation.getRemovedEdges().size());
		
		communitySeparation.clear();
		communitySeparation.setSource(23);
		assertEquals(0, communitySeparation.getRemovedEdges().size());
		try {
			communitySeparation.compute();
			fail("Must throw AdditionalSourcesNotSpecifiedException");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Dominating set test.
	 */
	@Test
	public void dominatingSetTest() {
		try {
			dominatingSet.compute();
			fail("Must throw AlgorithmNotInitializedException");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		dominatingSet.init(graph);
		dominatingSet.compute();
		
		assertTrue(dominatingSet.getDominatingSet().contains("4"));
		assertTrue(dominatingSet.getDominatingSet().contains("7"));
		assertTrue(dominatingSet.getDominatingSet().contains("12"));
		
		dominatingSet.clear();
		assertEquals(0, dominatingSet.getDominatingSet().size());
		
	}
	
	/**
	 * Egonet test.
	 */
	@Test
	public void egonetTest() {
		try {
			egonet.compute();
			fail("Must throw AlgorithmNotInitializedException");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		egonet.init(graph);
		egonet.setSource("14");
		try {
			egonet.compute();
			fail("Must throw AdditionalSourcesNotSpecifiedException");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		egonet.setSource("1");
		egonet.compute();
		assertEquals(3, egonet.getEgonet().getNodesNumber());
		assertEquals(4, egonet.getEgonet().getEdgesNumber());
		
		egonet.clear();
		assertEquals(0, egonet.getEgonet().getNodesNumber());
		assertEquals(0, egonet.getEgonet().getEdgesNumber());
	}
	
	/**
	 * Scc test.
	 */
	@Test
	public void sccTest() {
		try {
			scc.compute();
			fail("Must throw AlgorithmNotInitializedException");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		scc.init(graph);
		assertEquals(0, scc.getSCCs().size());
		
		scc.compute();
		assertEquals(1, scc.getSCCs().size());
		
		scc.clear();
		assertEquals(0, scc.getSCCs().size());
	}
	
	/**
	 * Shortest path from to test.
	 */
	@Test
	public void shortestPathFromToTest() {
		try {
			shortestPathFromTo.compute();
			fail("Must throw AlgorithmNotInitializedException");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		shortestPathFromTo.init(graph);
		shortestPathFromTo.setSource("1", "14");
		try {
			egonet.compute();
			fail("Must throw AdditionalSourcesNotSpecifiedException");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		shortestPathFromTo.setSource("1", "7");
		assertEquals(0, shortestPathFromTo.getPath().size());
		
		shortestPathFromTo.compute();
		assertTrue(shortestPathFromTo.getPath().contains("5"));
		assertTrue(shortestPathFromTo.getPath().contains("6"));
		assertEquals(4, shortestPathFromTo.getPath().size());
		
		shortestPathFromTo.clear();
		assertEquals(0, shortestPathFromTo.getPath().size());
		
	}
	
	/**
	 * Shortest paths from test.
	 */
	@Test
	public void shortestPathsFromTest() {
		try {
			shortestPathsFrom.compute();
			fail("Must throw AlgorithmNotInitializedException");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		shortestPathsFrom.init(graph);
		shortestPathsFrom.setSource("14");
		try {
			egonet.compute();
			fail("Must throw AdditionalSourcesNotSpecifiedException");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		shortestPathsFrom.setSource("1");
		assertEquals(0, shortestPathsFrom.getPaths().size());
		
		shortestPathsFrom.compute();
		assertEquals(11, shortestPathsFrom.getPaths().size());
		
		shortestPathsFrom.clear();
		assertEquals(0, shortestPathsFrom.getPaths().size());
	}
	
	/**
	 * Shortest paths test.
	 */
	@Test
	public void shortestPathsTest() {
		try {
			shortestPaths.compute();
			fail("Must throw AlgorithmNotInitializedException");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		shortestPaths.init(graph);
		shortestPaths.compute();
		assertEquals(6, shortestPaths.getLongestPathLength());
		assertEquals(132, shortestPaths.getPaths().size());
		
		shortestPaths.clear();
		assertEquals(0, shortestPaths.getLongestPathLength());
		assertEquals(0, shortestPaths.getPaths().size());
	}
	
	/**
	 * Graph loader test.
	 */
	@Test
	public void graphLoaderTest() {
		Graph gr = new BasicGraph();
		gr.addEdge("1", "2");
		gr.addEdge("2", "3");
		gr.addEdge("3", "4");
		gr.addEdge("3", "5");
		GraphLoader.saveGraph(gr, "graph_loader_test.txt");
		
		Graph gr2 = new BasicGraph();
		GraphLoader.loadGraph(gr2, "graph_loader_test.txt");
		assertEquals(5, gr2.getNodesNumber());
		assertEquals(4, gr2.getEdgesNumber());		
	}
	
	/**
	 * End.
	 */
	@AfterClass
    public static void end() {
		File file = new File("graph_loader_test.txt");
		file.delete();
    }

}
