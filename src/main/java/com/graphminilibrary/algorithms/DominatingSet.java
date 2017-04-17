package com.graphminilibrary.algorithms;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import com.graphminilibrary.graph.Graph;

/**
 * Implementation of the algorithm for computing the dominating set of the algorithm.
 */
public class DominatingSet implements Algorithm {
	
	/** The graph on which the algorithm will perform compute. */
	private Graph graph;
	
	/** The dominating set. */
	private Set<String> dominatingSet;
	
	/** The boolean indicating the initialization of the algorithm. */
	private boolean initialized;

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.algorithms.Algorithm#init(com.anarsultanov.graphminilibrary.graph.Graph)
	 */
	@Override
	public void init(Graph graph) {
		this.graph = graph;
		this.dominatingSet = new HashSet<String>();
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
			Set<String> marked = new HashSet<String>();
			NavigableSet<Entry<String, Integer>> queue = new TreeSet<Entry<String, Integer>>(new Comparator<Entry<String, Integer>>() {
				public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
					int res = arg0.getValue().compareTo(arg1.getValue());
					return res != 0 ? res : 1;
				}});
				
			graph.getAdjacencyListMap().forEach((k,v) -> {
				queue.add(new AbstractMap.SimpleEntry<String, Integer> (k, v.size()));
			});
			
			while(!queue.isEmpty()) {
				Entry<String, Integer> e = queue.pollLast();
				String v = e.getKey();
				if (!dominatingSet.contains(v) && !marked.contains(v)) {
					dominatingSet.add(v);
					marked.addAll(graph.getAdjacencyListMap().get(v));
				}
			}
		}
	}
	
	/**
	 * Gets the dominating set.
	 *
	 * @return the dominating set
	 */
	public Set<String> getDominatingSet() {
		return dominatingSet;
	}

	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.algorithms.Algorithm#clear()
	 */
	@Override
	public void clear() {
		this.dominatingSet = new HashSet<String>();
	}

}
