package com.graphminilibrary.algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.graphminilibrary.graph.BasicGraph;
import com.graphminilibrary.graph.Graph;

/**
 * Implementation of the algorithm for computing strongly connected components of the graph.
 */
public class SCC implements Algorithm{
	
	/** The graph on which the algorithm will perform compute. */
	private Graph graph;
	
	/** The reverse representation of the graph.  */
	private Graph reverseGraph;
	
	/** The list of strongly connected components presented as graphs. */
	private List<Graph> sccGraphs;
	
	/** The boolean indicating the initialization of the algorithm. */
	private boolean initialized;

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.algorithms.Algorithm#init(com.anarsultanov.graphminilibrary.graph.Graph)
	 */
	@Override
	public void init(Graph graph) {
		this.graph = graph;
		sccGraphs = new ArrayList<Graph>();
		reverseGraph();
		this.initialized = true;
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.algorithms.Algorithm#compute()
	 */
	@Override
	public void compute() {
		if(!initialized) {
			throw new RuntimeException("The algorithm is not initialized.");
		} else {
			Set<String> visited = new HashSet<String>();
			Stack<String> stack = new Stack<String>();
	
			graph.getAdjacencyListMap().keySet().forEach(k -> {
				if (!visited.contains(k))
					sccFirstMethod(graph, k, visited, stack);
			});
	
			visited.clear();
	
			while (!stack.isEmpty()) {
				String v = stack.pop();
				HashSet<String> sccVertices = new HashSet<String>();
				if (!visited.contains(v)) {
					sccSecondMethod(reverseGraph, v, visited, sccVertices);
					BasicGraph sccGraph = new BasicGraph();
					sccVertices.forEach(vertex -> sccGraph.addNode(vertex));
					sccGraph.getAdjacencyListMap().keySet().forEach(key -> {
						graph.getAdjacencyListMap().get(key).forEach(k -> {
							if (sccGraph.hasNode(k))
								sccGraph.addEdge(key, k);
						});
					});
					sccGraphs.add(sccGraph);
				}
			}
		}
	}
	
	/**
	 * Helper method.
	 *
	 * @param graph the graph
	 * @param node the node
	 * @param visited the visited
	 * @param stack the stack
	 */
	private void sccFirstMethod(Graph graph, String node, Set<String> visited, Stack<String> stack) {
		visited.add(node);
		graph.getAdjacencyListMap().get(node).forEach(n -> {
			if (!visited.contains(n))
				sccFirstMethod(graph, n, visited, stack);
		});
		stack.push(node);
	}

	/**
	 * Helper method.
	 *
	 * @param graph the graph
	 * @param node the node
	 * @param visited the visited
	 * @param sccVertices the scc vertices
	 */
	private void sccSecondMethod(Graph graph, String node, Set<String> visited, HashSet<String> sccVertices) {

		visited.add(node);
		sccVertices.add(node);
		if (!graph.hasNode(node))
			return;
		graph.getAdjacencyListMap().get(node).forEach(neighbor -> {
			if (!visited.contains(neighbor)) {
				sccSecondMethod(graph, neighbor, visited, sccVertices);
			}
		});
	}
	
	/**
	 * Method for reversing the graph.
	 *
	 * @return the reverse representation of the graph.
	 */
	private Graph reverseGraph() {
		reverseGraph = new BasicGraph();
		graph.getAdjacencyListMap().keySet().forEach(k -> {
			graph.getAdjacencyListMap().get(k).forEach(l -> {
				reverseGraph.addEdge(l, k);
			});
		});
		return reverseGraph;
	}
	
	/**
	 * Gets the list of strongly connected components presented as graphs..
	 *
	 * @return the list of strongly connected components presented as graphs.
	 */
	public List<Graph> getSCCs() {
		return sccGraphs;
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.algorithms.Algorithm#clear()
	 */
	@Override
	public void clear() {
		this.sccGraphs = new ArrayList<Graph>();
	}

}
