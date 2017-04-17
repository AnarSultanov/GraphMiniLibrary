package com.graphminilibrary.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.graphminilibrary.graph.Graph;

/**
 * Implementation of the algorithm for computing the shortest path from all nodes to all others.
 */
public class ShortestPaths implements Algorithm {

	/** The graph on which the algorithm will perform compute. */
	private Graph graph;
	
	/** The paths list. */
	private List<List<String>> pathsList;

	/** The longest path. */
	private List<String> longestPath;
	
	/** The average length. */
	private int averageLength;
	
	/** The boolean indicating the initialization of the algorithm. */
	private boolean initialized;

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.algorithms.Algorithm#init(com.anarsultanov.graphminilibrary.graph.Graph)
	 */
	@Override
	public void init(Graph graph) {
		this.graph = graph;
		this.pathsList = new ArrayList<List<String>>();
		this.longestPath = null;
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
			ShortestPathsFrom spf = new ShortestPathsFrom();
			spf.init(graph);
			for (String node : graph.getAdjacencyListMap().keySet()) {
				spf.setSource(node);
				spf.compute();
				pathsList.addAll(spf.getPaths());
				spf.clear();
			}
		}
	}

	/**
	 * Gets the paths.
	 *
	 * @return the paths
	 */
	public List<List<String>> getPaths() {
		return pathsList;
	}

	/**
	 * Gets the longest path.
	 *
	 * @return the longest path
	 */
	public List<String> getLongestPath() {
		if (longestPath == null) {
			int maxLength = 0;
			for (List<String> path : pathsList) {
				if (path.size() > maxLength) {
					maxLength = path.size();
					longestPath = path;
				}
			}
		}

		return longestPath;
	}

	/**
	 * Gets the longest path length.
	 *
	 * @return the longest path length
	 */
	public int getLongestPathLength() {
		if (pathsList.size() == 0) {
			return 0;
		}
		if (longestPath == null) {
			getLongestPath();
		}
		return longestPath.size();
	}

	/**
	 * Gets the average path length.
	 *
	 * @return the average path length
	 */
	public int getAveragePathLength() {
		if (pathsList.size() == 0) {
			return 0;
		}
		if (averageLength == 0) {
			int allLength = 0;
			for (List<String> path : pathsList) {
				allLength += path.size();
			}
			averageLength = allLength / pathsList.size();
		}
		return averageLength;
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.algorithms.Algorithm#clear()
	 */
	@Override
	public void clear() {
		this.pathsList = new ArrayList<List<String>>();
		this.longestPath = null;
	}
}
