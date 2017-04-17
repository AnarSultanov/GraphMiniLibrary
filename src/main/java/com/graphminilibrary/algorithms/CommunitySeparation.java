package com.graphminilibrary.algorithms;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import com.graphminilibrary.graph.BasicGraph;
import com.graphminilibrary.graph.Graph;

/**
 * Implementation of the algorithm for computing the betweennes of edges in the graph
 * and removing the specified number of edges with the largest betweennes.
 */
public class CommunitySeparation implements Algorithm {
	
	/** The graph on which the algorithm will perform compute. */
	private Graph graph;
	
	/** The graph obtained by removing edges.  */
	private Graph separatedGraph;
	
	/** The list of removed edges. */
	private List<List<String>> removedEdges;
	
	/** The number of edges to remove. */
	private int numberOfEdgesToRemove;
	
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
		this.separatedGraph = new BasicGraph();
		this.removedEdges = new ArrayList<List<String>>();
		this.initialized = true;
	}
	
	/**
	 * Sets the additional sources required to compute.
	 *
	 * @param numberOfEdgesToRemove the number of edges to remove.
	 */
	public void setSource(int numberOfEdgesToRemove) {
		if (numberOfEdgesToRemove <= graph.getEdgesNumber()) {
			this.numberOfEdgesToRemove = numberOfEdgesToRemove;
			this.sourcesSpecified = true;
		} else {
			System.err.println("Specified number is greater than the number of edges!");
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
			copyAdjacencyListMap(graph, separatedGraph);
			
			NavigableSet<Entry<Entry<String,String>, Integer>> queue = new TreeSet<Entry<Entry<String,String>, Integer>>(comp);
			
			for (int i = 0; i<numberOfEdgesToRemove; i++) {
				queue.clear();
				queue.addAll(getBetweennes(separatedGraph).entrySet());
				
				Entry<Entry<String, String>, Integer> last = queue.pollLast();
				String v1 = last.getKey().getKey();
				String v2 = last.getKey().getValue();
				
				separatedGraph.removeEdge(v1, v2);
				removedEdges.add(new ArrayList<>(Arrays.asList(v1, v2)));
				if (separatedGraph.hasEdge(v2, v1)) {
					separatedGraph.removeEdge(v2, v1);
					removedEdges.add(new ArrayList<>(Arrays.asList(v2, v1)));
				}		
			}
		}
	}
	
	/**
	 * Copy adjacency list map from one graph to another.
	 *
	 * @param source the source graph
	 * @param goal the goal graph
	 */
	private void copyAdjacencyListMap(Graph source, Graph goal) {
		for(String from: source.getAdjacencyListMap().keySet()) {
			for (String to: source.getAdjacencyListMap().get(from)) {
				goal.addEdge(from, to);
			}
		}
	}

	/**
	 * Gets the betweennes of edges in the graph.
	 *
	 * @param graph the graph
	 * @return the betweennes map
	 */
	private Map<Entry<String,String>, Integer> getBetweennes (Graph graph) {
		
		Map<Entry<String,String>, Integer>betweennesMap = new HashMap<Entry<String,String>, Integer> ();
		
		for(String start: graph.getAdjacencyListMap().keySet()) {
			HashMap<String, String> parentMap = new HashMap<String, String>();
			Queue<String> toExplore = new LinkedList<String>();
			HashSet<String> visited = new HashSet<String>();
			
			toExplore.add(start);
			String next = null;
	
			while (!toExplore.isEmpty()) {
				next = toExplore.remove();
	
				Set<String> neighbors = graph.getAdjacencyListMap().get(next);
				for (String n : neighbors) {
					if (!visited.contains(n)) {
						visited.add(n);
						parentMap.put(n, next);
						toExplore.add(n);
					}
				}
			}
			
			for(String s: parentMap.keySet()) {
				String current = s;
				while (!current.equals(start)) {
					Entry<String, String> edge = new AbstractMap.SimpleEntry<String, String>(parentMap.get(current), current);
					if(betweennesMap.containsKey(edge)){
						int betweennes = betweennesMap.get(edge) + 1;
						betweennesMap.replace(edge, betweennes);
					} else {
						betweennesMap.put(edge, 1);
					}
					current = parentMap.get(current);
				}
			}
		}
		return betweennesMap;
	}
		
	/** The comparator. */
	Comparator<Entry<Entry<String,String>, Integer>> comp = new Comparator<Entry<Entry<String,String>, Integer>>() {
		public int compare(Entry<Entry<String,String>, Integer> arg0, Entry<Entry<String,String>, Integer> arg1) {
			int res = arg0.getValue().compareTo(arg1.getValue());
			return res != 0 ? res : 1;
		}
	};
	
	/**
	 * Gets the graph obtained by removing edges.
	 *
	 * @return the separated graph
	 */
	public Graph getSeparatedGraph(){
		return separatedGraph;
	}
	
	/**
	 * Gets the list of removed edges.
	 *
	 * @return the list of removed edges
	 */
	public List<List<String>> getRemovedEdges(){
		return removedEdges;
	}
	
	/* (non-Javadoc)
	 * @see com.anarsultanov.graphminilibrary.algorithms.Algorithm#clear()
	 */
	@Override
	public void clear() {
		this.separatedGraph = new BasicGraph();
		this.removedEdges = new ArrayList<List<String>>();
		this.numberOfEdgesToRemove = 0;
		this.sourcesSpecified = false;
	}

}
