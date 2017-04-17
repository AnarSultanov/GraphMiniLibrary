package com.graphminilibrary.graph;

import java.util.Map;
import java.util.Set;

/**
 * The Graph Interface.
 */
public interface Graph {
	
	/**
	 * Gets the adjacency list map.
	 *
	 * @return the adjacency list map
	 */
	public Map<String, Set<String>> getAdjacencyListMap();
	
	/**
	 * Gets the nodes number.
	 *
	 * @return the nodes number
	 */
	public int getNodesNumber();
	
	/**
	 * Gets the edges number.
	 *
	 * @return the edges number
	 */
	public int getEdgesNumber();
	
	/**
	 * Adds the node.
	 *
	 * @param node the node
	 */
	public void addNode(String node);
	
	/**
	 * Removes the node.
	 *
	 * @param node the node
	 */
	public void removeNode(String node);	
	
	/**
	 * Checks for node.
	 *
	 * @param node the node
	 * @return true, if successful
	 */
	public boolean hasNode(String node);
	
	/**
	 * Node's out edges.
	 *
	 * @param node the node
	 * @return the sets the
	 */
	public Set<String> outEdges(String node);
	
	/**
	 * Node's in edges.
	 *
	 * @param node the node
	 * @return the sets the
	 */
	public Set<String> inEdges(String node);
	
	/**
	 * Adds the edge.
	 *
	 * @param from the from node
	 * @param to the to node
	 */
	public void addEdge(String from, String to);
	
	/**
	 * Removes the edge.
	 *
	 * @param from the from node
	 * @param to the to node
	 */
	public void removeEdge(String from, String to);	
	
	/**
	 * Checks for edge.
	 *
	 * @param from the from node
	 * @param to the to node
	 * @return true, if successful
	 */
	public boolean hasEdge(String from, String to);
	
	/**
	 * Removes all nodes and edges.
	 */
	public void clear();
}
