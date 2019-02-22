package com.anarsultanov.graphminilibrary.graph;

import java.util.Map;
import java.util.Set;

/**
 * The Graph Interface.
 */
public interface Graph<T> {

	/**
	 * Gets the adjacency list map.
	 *
	 * @return the adjacency list map
	 */
	Map<T, Set<T>> getAdjacencyListMap();

	/**
	 * Gets the nodes number.
	 *
	 * @return the nodes number
	 */
	int getNodesNumber();

	/**
	 * Gets the edges number.
	 *
	 * @return the edges number
	 */
	int getEdgesNumber();

	/**
	 * Adds the node.
	 *
	 * @param node the node
	 */
	void addNode(T node);

	/**
	 * Removes the node.
	 *
	 * @param node the node
	 */
	void removeNode(T node);

	/**
	 * Checks for node.
	 *
	 * @param node the node
	 * @return true, if successful
	 */
	boolean hasNode(T node);

	/**
	 * Node's out edges.
	 *
	 * @param node the node
	 * @return the sets the
	 */
	Set<T> outEdges(T node);

	/**
	 * Node's in edges.
	 *
	 * @param node the node
	 * @return the sets the
	 */
	Set<T> inEdges(T node);

	/**
	 * Adds the edge.
	 *
	 * @param from the from node
	 * @param to the to node
	 */
	void addEdge(T from, T to);

	/**
	 * Removes the edge.
	 *
	 * @param from the from node
	 * @param to the to node
	 */
	void removeEdge(T from, T to);

	/**
	 * Checks for edge.
	 *
	 * @param from the from node
	 * @param to the to node
	 * @return true, if successful
	 */
	boolean hasEdge(T from, T to);

	/**
	 * Removes all nodes and edges.
	 */
	void clear();
}
