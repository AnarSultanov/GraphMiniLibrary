package com.graphminilibrary.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.graphminilibrary.graph.Graph;

/**
 * Implementation of the algorithm for computing the shortest path from one node to another.
 */
public class ShortestPathFromTo implements Algorithm {

	/** The graph on which the algorithm will perform compute. */
	private Graph graph;
	
	/** The path represented as list. */
	private LinkedList<String> path;
	
	/** The starting node of path. */
	private String from;
	
	/** The end node of path. */
	private String to;
	
	/** The boolean indicating the initialization of the algorithm. */
	private boolean initialized;
	
	/** The boolean indicating whether additional sources are specified. */
	private boolean sourcesSpecified;
	
	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.algorithms.Algorithm#init(com.anarsultanov.graphminilibrary.graph.Graph)
	 */
	@Override
	public void init(Graph graph) {
		this.graph = graph;
		this.path = new LinkedList<String>();
		this.initialized = true;
	}
	
	/**
	 * Sets the additional sources required to compute.
	 *
	 * @param from the starting node of path.
	 * @param to the end node of path.
	 */
	public void setSource(String from, String to) {
		if (!graph.hasNode(from)) {
			System.err.println("Start node " + from + " does not exist");
		} else if (!graph.hasNode(to)) {
			System.err.println("End node " + to + " does not exist");
		} else {
			this.from = from;
			this.to = to;
			this.sourcesSpecified = true;
		}
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.algorithms.Algorithm#compute()
	 */
	@Override
	public void compute() {
		if(!initialized) {
			throw new RuntimeException("The algorithm is not initialized.");
		} else if(!sourcesSpecified) {
			throw new RuntimeException("Additional sources are not specified.");
		} else {
			bfs(from, to);
		}
	}
	
	/**
	 * BFS algorithm implementation.
	 *
	 * @param from the starting node of path.
	 * @param to the end node of path.
	 */
	private void bfs(String from, String to) {
		
		HashMap<String, String> parentMap = new HashMap<String, String>();
		Queue<String> toExplore = new LinkedList<String>();
		HashSet<String> visited = new HashSet<String>();
		
		toExplore.add(from);
		String next = null;

		while (!toExplore.isEmpty()) {
			next = toExplore.remove();

			if (next.equals(to))
				break;

			Set<String> neighbors = graph.getAdjacencyListMap().get(next);
			for (String n : neighbors) {
				if (!visited.contains(n)) {
					visited.add(n);
					parentMap.put(n, next);
					toExplore.add(n);
				}
			}
		}
		
		if (!next.equals(to)) {
			System.out.println("No path found from " + from + " to " + to);
		} else {
			reconstructPath(parentMap, from, to);
		}
	}
	
	/**
	 * Method for reconstructing the path.
	 *
	 * @param parentMap the parent map
	 * @param from the starting node of path.
	 * @param to the end node of path.
	 */
	private void reconstructPath(HashMap<String, String> parentMap, String from, String end) {
		String current = to;

		while (!current.equals(from)) {
			path.addFirst(current);
			current = parentMap.get(current);
		}

		path.addFirst(from);
	}

	
	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public List<String> getPath() {
		return path;
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.algorithms.Algorithm#clear()
	 */
	@Override
	public void clear() {
		this.path = new LinkedList<String>();
		this.from = null;
		this.to = null;
		this.sourcesSpecified = false;
	}

}
