package com.anarsultanov.graphminilibrary.utilities;

import com.anarsultanov.graphminilibrary.graph.Graph;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Saves and loads the graph.
 */
public class GraphLoader {

    /**
     * Load graph from file.
     *
     * @param graph    the graph
     * @param filename the filename
     * @param <T> the graph nodes type
     */
    public static <T> void loadGraph(Graph<T> graph, String filename) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                T t1 = (T) objectInputStream.readObject();
                if (t1 != null) {
                    T t2 = (T) objectInputStream.readObject();
                    if (t2 == null) {
                        throw new IllegalStateException();
                    }
                    graph.addEdge(t1, t2);
                }
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save graph to file.
     *
     * @param graph    the graph
     * @param filename the filename
     */
    public static void saveGraph(Graph<? extends Serializable> graph, String filename) {
        try (ObjectOutputStream objectOutputStream
                     = new ObjectOutputStream(new FileOutputStream(filename))) {
            for (Serializable from : graph.getAdjacencyListMap().keySet()) {
                for (Serializable to : graph.getAdjacencyListMap().get(from)) {
                    objectOutputStream.writeObject(from);
                    objectOutputStream.writeObject(to);
                }
            }
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
