package com.graphminilibrary.utilities;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import com.graphminilibrary.graph.Graph;

/**
 * Saves and loads the graph.
 */
public class GraphLoader {
    
    /**
     * Load graph from file.
     *
     * @param graph the graph
     * @param filename the filename
     */
    public static void loadGraph(Graph graph, String filename) {
        Scanner sc;
        
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        while (sc.hasNext()) {
            String v1 = sc.next();
            String v2 = sc.next();
            graph.addEdge(v1, v2);
        }
        
        sc.close();
    }
    
    /**
     * Save graph to file.
     *
     * @param graph the graph
     * @param filename the filename
     */
    public static void saveGraph(Graph graph, String filename) {
    	PrintWriter out;
        
        try {
        	out = new PrintWriter(filename);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        for(String from: graph.getAdjacencyListMap().keySet()) {
			for (String to: graph.getAdjacencyListMap().get(from)) {
				out.println(from + " " + to);
			}
		}
        
        out.close();
    }
}
