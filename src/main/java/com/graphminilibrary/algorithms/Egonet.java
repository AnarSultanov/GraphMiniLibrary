package com.graphminilibrary.algorithms;

import java.util.Set;

import com.graphminilibrary.graph.BasicGraph;
import com.graphminilibrary.graph.Graph;

/**
 * Implementation of the algorithm for computing the egonet of the specified node.
 */
public class Egonet implements Algorithm {

	/** The graph on which the algorithm will perform compute. */
	private Graph graph;
	
	/** The egonet. */
	private Graph egonet;
	
	/** The center node of the egonet. */
	private String center;

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
		this.egonet = new BasicGraph();
		this.initialized = true;
	}

	/**
	 * Sets the additional sources required to compute.
	 *
	 * @param center the center node of the egonet
	 */
	public void setSource(String center) {
		if (graph.hasNode(center)) {
			this.center = center;
			this.sourcesSpecified = true;
		} else {
			System.err.println("Node " + center + " does not exist");
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
			egonet.addNode(center);
			Set<String> neighbors = graph.outEdges(center);
			neighbors.forEach(n -> egonet.addNode(n));
	
			egonet.getAdjacencyListMap().keySet().forEach(k -> {
				graph.getAdjacencyListMap().get(k).forEach(v -> {
					if (egonet.hasNode(v))
						egonet.addEdge(k, v);
				});
			});
		}
	}

	/**
	 * Gets the egonet.
	 *
	 * @return the egonet
	 */
	public Graph getEgonet() {
		return egonet;
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.algorithms.Algorithm#clear()
	 */
	@Override
	public void clear() {
		this.egonet = new BasicGraph();
		this.center = null;
		this.sourcesSpecified = false;
	}
}
