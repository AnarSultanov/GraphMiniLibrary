package com.graphminilibrary.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Graph implementation.
 */
public class BasicGraph implements Graph{

	/** The adjacency list map. */
	private Map<String, Set<String>> adjacencyListMap;
	
	/** The nodes number. */
	private int nodesNumber;
	
	/** The edges number. */
	private int edgesNumber;

	/**
	 * Instantiates a new basic graph.
	 */
	public BasicGraph() {
		this.adjacencyListMap = new HashMap<String, Set<String>>();
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.graph.Graph#getAdjacencyListMap()
	 */
	@Override
	public Map<String, Set<String>> getAdjacencyListMap() {
		return adjacencyListMap;
	}
	
	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.graph.Graph#getNodesNumber()
	 */
	@Override
	public int getNodesNumber() {
		return nodesNumber;
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.graph.Graph#getEdgesNumber()
	 */
	@Override
	public int getEdgesNumber() {
		return edgesNumber;
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.graph.Graph#addNode(java.lang.String)
	 */
	@Override
	public void addNode(String node) {
		if (!this.adjacencyListMap.containsKey(node)) {
			this.adjacencyListMap.put(node, new HashSet<String>());
			nodesNumber++;
		}
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.graph.Graph#removeNode(java.lang.String)
	 */
	@Override
	public void removeNode(String node) {
		if (this.hasNode(node)) {
			this.adjacencyListMap.remove(node);
			for(String n: this.adjacencyListMap.keySet()) {
				if (this.adjacencyListMap.get(n).contains(node))
					this.adjacencyListMap.get(n).remove(node);
			}
			nodesNumber--;
		}		
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.graph.Graph#outEdges(java.lang.String)
	 */
	@Override
	public Set<String> outEdges(String node) {
		if (this.adjacencyListMap.containsKey(node))
			return this.adjacencyListMap.get(node);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.graph.Graph#inEdges(java.lang.String)
	 */
	@Override
	public Set<String> inEdges(String node) {
		if (this.adjacencyListMap.containsKey(node)) {
			Set<String> inEdges = new HashSet<>();
			for(String n: this.adjacencyListMap.keySet()) {
				if (this.adjacencyListMap.get(n).contains(node))
					inEdges.add(n);
			}
			return inEdges;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.graph.Graph#addEdge(java.lang.String, java.lang.String)
	 */
	@Override
	public void addEdge(String from, String to) {
		if (!hasNode(from)) {
			addNode(from);
		}
		if (!hasNode(to)) {
			addNode(to);
		}
		if (!hasEdge(from, to)) {
			this.adjacencyListMap.get(from).add(to);	
			edgesNumber++;
		}
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.graph.Graph#removeEdge(java.lang.String, java.lang.String)
	 */
	@Override
	public void removeEdge(String from, String to) {
		if (hasEdge(from, to)) {
			this.adjacencyListMap.get(from).remove(to);
			edgesNumber--;
		}	
	}
	
	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.graph.Graph#hasNode(java.lang.String)
	 */
	@Override
	public boolean hasNode(String node) {
		if (this.adjacencyListMap.containsKey(node))
				return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.graph.Graph#hasEdge(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hasEdge(String from, String to) {
		if (hasNode(from))
			if (this.adjacencyListMap.get(from).contains(to))
				return true;
		return false;
	}


	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.graph.Graph#clear()
	 */
	@Override
	public void clear() {
		this.adjacencyListMap = new HashMap<String, Set<String>>();
		this.nodesNumber = 0;
		this.edgesNumber = 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adjacencyListMap == null) ? 0 : adjacencyListMap.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicGraph other = (BasicGraph) obj;
		if (adjacencyListMap == null) {
			if (other.adjacencyListMap != null)
				return false;
		} else if (!adjacencyListMap.equals(other.adjacencyListMap))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Graph Map = " + adjacencyListMap;
	}
}
