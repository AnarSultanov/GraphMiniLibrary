package com.graphminilibrary.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.graphminilibrary.graph.Graph;

/**
 * Implementation of the algorithm for computing the shortest path from one node to all others.
 */
public class ShortestPathsFrom implements Algorithm {

	/** The graph on which the algorithm will perform compute. */
	private Graph graph;
	
	/** The paths list. */
	private List<List<String>> pathsList;

	/** The starting node of path. */
	private String from;
	
	/** The longest path. */
	private List<String> longestPath;
	
	/** The average length. */
	private int averageLength;
	
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
		this.pathsList = new ArrayList<List<String>>();
		this.longestPath = null;
		this.initialized = true;
	}

	/**
	 * Sets the additional sources required to compute.
	 *
	 * @param from the starting node of paths.
	 */
	public void setSource(String from) {
		if (!graph.getAdjacencyListMap().containsKey(from)) {
			System.err.println("Start node " + from + " does not exist");
		} else {
			this.from = from;
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
			dijkstra(from);
		}
	}
	
	/**
	 * Dijkstra's algorithm implementation.
	 *
	 * @param from the starting node of paths.
	 */
	private void dijkstra(String from) {
		Set<String> settledNodes = new HashSet<String>();
		Map<String, List<String>> unsettledNodes = new HashMap<String, List<String>>();
		unsettledNodes.put(from, new LinkedList<String>());

		while (!unsettledNodes.isEmpty()) {
			Entry<String, List<String>> current = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(current.getKey());

			if (from != current.getKey()) {
				List<String> pathTemp = new ArrayList<String>(current.getValue());
				pathTemp.add(current.getKey());
				pathsList.add(pathTemp);
			}

			for (String next : graph.getAdjacencyListMap().get(current.getKey())) {
				if (!settledNodes.contains(next)) {
					if (unsettledNodes.containsKey(next)) {
						List<String> path = new LinkedList<String>(current.getValue());
						path.add(current.getKey());
						int oldDist = unsettledNodes.get(next).size();
						int newDist = path.size();
						if (oldDist > newDist) {
							unsettledNodes.put(next, path);
						}
					} else {
						List<String> path = new LinkedList<String>(current.getValue());
						path.add(current.getKey());
						unsettledNodes.put(next, path);
					}
				}
			}
			settledNodes.add(current.getKey());
		}
	}

	/**
	 * Gets the lowest distance node.
	 *
	 * @param unsettledNodes the unsettled nodes
	 * @return the lowest distance node
	 */
	private Entry<String, List<String>> getLowestDistanceNode(Map<String, List<String>> unsettledNodes) {
		Entry<String, List<String>> lowestDistanceNode = null;
	    int lowestDistance = Integer.MAX_VALUE;
	    for (Entry<String, List<String>> node: unsettledNodes.entrySet()) {
	        int nodeDistance = node.getValue().size();
	        if (nodeDistance < lowestDistance) {
	            lowestDistance = nodeDistance;
	            lowestDistanceNode = node;
	        }
	    }
	    return lowestDistanceNode;
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
			for (List<String> path: pathsList) {
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
		if(averageLength == 0) {
			int allLength = 0;
			for (List<String> path: pathsList) {
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
		this.from = null;
		this.longestPath = null;
		this.sourcesSpecified = false;
	}
}
