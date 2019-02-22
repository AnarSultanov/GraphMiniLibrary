package com.anarsultanov.graphminilibrary.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Graph implementation.
 */
public class BasicGraph<T> implements Graph<T> {

    private Map<T, Set<T>> adjacencyListMap;
    private int nodesNumber;
    private int edgesNumber;

    /**
     * Instantiates a new basic graph.
     */
    public BasicGraph() {
        this.adjacencyListMap = new HashMap<>();
    }

    /* (non-Javadoc)
     * @see com.anarsultanov.graphminilibrary.graph.Graph#getAdjacencyListMap()
     */
    @Override
    public Map<T, Set<T>> getAdjacencyListMap() {
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
    public void addNode(T node) {
        if (!this.adjacencyListMap.containsKey(node)) {
            this.adjacencyListMap.put(node, new HashSet<>());
            nodesNumber++;
        }
    }

    /* (non-Javadoc)
     * @see com.anarsultanov.graphminilibrary.graph.Graph#removeNode(java.lang.String)
     */
    @Override
    public void removeNode(T node) {
        if (this.hasNode(node)) {
            this.adjacencyListMap.remove(node);
            for (T n : this.adjacencyListMap.keySet()) {
                this.adjacencyListMap.get(n).remove(node);
            }
            nodesNumber--;
        }
    }

    /* (non-Javadoc)
     * @see com.anarsultanov.graphminilibrary.graph.Graph#outEdges(java.lang.String)
     */
    @Override
    public Set<T> outEdges(T node) {
        if (this.adjacencyListMap.containsKey(node))
            return this.adjacencyListMap.get(node);
        return null;
    }

    /* (non-Javadoc)
     * @see com.anarsultanov.graphminilibrary.graph.Graph#inEdges(java.lang.String)
     */
    @Override
    public Set<T> inEdges(T node) {
        if (this.adjacencyListMap.containsKey(node)) {
            Set<T> inEdges = new HashSet<>();
            for (T n : this.adjacencyListMap.keySet()) {
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
    public void addEdge(T from, T to) {
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
    public void removeEdge(T from, T to) {
        if (hasEdge(from, to)) {
            this.adjacencyListMap.get(from).remove(to);
            edgesNumber--;
        }
    }

    /* (non-Javadoc)
     * @see com.anarsultanov.graphminilibrary.graph.Graph#hasNode(java.lang.String)
     */
    @Override
    public boolean hasNode(T node) {
        return this.adjacencyListMap.containsKey(node);
    }

    /* (non-Javadoc)
     * @see com.anarsultanov.graphminilibrary.graph.Graph#hasEdge(java.lang.String, java.lang.String)
     */
    @Override
    public boolean hasEdge(T from, T to) {
        if (hasNode(from))
            return this.adjacencyListMap.get(from).contains(to);
        return false;
    }


    /* (non-Javadoc)
     * @see com.anarsultanov.graphminilibrary.graph.Graph#clear()
     */
    @Override
    public void clear() {
        this.adjacencyListMap = new HashMap<>();
        this.nodesNumber = 0;
        this.edgesNumber = 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals()
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicGraph<?> that = (BasicGraph<?>) o;
        return nodesNumber == that.nodesNumber &&
                edgesNumber == that.edgesNumber &&
                Objects.equals(adjacencyListMap, that.adjacencyListMap);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(adjacencyListMap, nodesNumber, edgesNumber);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BasicGraph{" +
                "adjacencyListMap=" + adjacencyListMap +
                ", nodesNumber=" + nodesNumber +
                ", edgesNumber=" + edgesNumber +
                '}';
    }
}
