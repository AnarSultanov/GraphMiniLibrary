package com.graphminilibrary.algorithms;

import com.graphminilibrary.graph.Graph;

/**
 * The Algorithm Interface.
 */
public interface Algorithm {
	
	/**
	 * Initializes the algorithm.
	 *
	 * @param graph The graph on which the algorithm will perform compute.
	 */
	public void init(Graph graph);
	
	/**
	 * Starts algorithm to compute. 
	 * Requires the initialization of the algorithm and setting of additional sources for some algorithms.
	 */
	public void compute();
	
	/**
	 * Clears results of compute and additional sources if they are set.
	 */
	public void clear();
}
