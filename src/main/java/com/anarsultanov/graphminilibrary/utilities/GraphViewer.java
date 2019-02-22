package com.anarsultanov.graphminilibrary.utilities;

import com.anarsultanov.graphminilibrary.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Displays the graph in the applet.
 */
public class GraphViewer {

    /**
     * Display graph.
     *
     * @param graph the graph
     * @param <T> the graph nodes type
     */
    public static <T> void displayGraph(Graph<T> graph) {
        JFrame mainWindow = new JFrame("Graph Viewer");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(800, 800);
        mainWindow.setLayout(new GridLayout());

        ViewerPanel<T> graphPanel = new ViewerPanel<>();
        mainWindow.add(graphPanel);

        mainWindow.setVisible(true);
        graphPanel.setVisible(true);

        Integer notesInLine = (int) (Math.ceil(Math.sqrt(graph.getAdjacencyListMap().keySet().size())) + 2);
        int dist = 800 / notesInLine;
        int[] nodePlacements = {dist, dist, dist, dist};

        HashMap<T, ViewerNode<T>> created = new HashMap<>();

        for (T v : graph.getAdjacencyListMap().keySet()) {

            ViewerNode<T> node = createNode(graphPanel, v, created, nodePlacements);

            for (T n : graph.getAdjacencyListMap().get(v)) {
                ViewerNode<T> neighbor = createNode(graphPanel, n, created, nodePlacements);
                node.addConnection(neighbor);
            }
        }
    }

    /**
     * Creates the node for viewer.
     *
     * @param graphPanel     the graph panel
     * @param name           the node's name
     * @param created        the created
     * @param nodePlacements the node placements
     * @return the viewer node
     */
    private static <T> ViewerNode<T> createNode(ViewerPanel<T> graphPanel, T name, HashMap<T, ViewerNode<T>> created,
                                                int[] nodePlacements) {

        int x = nodePlacements[0];
        int y = nodePlacements[1];
        int z = nodePlacements[2];
        int dist = nodePlacements[3];

        ViewerNode<T> node;
        if (created.containsKey(name)) {
            node = created.get(name);
        } else {
            node = new ViewerNode<>(graphPanel, name);
            node.setVisible(true);
            created.put(name, node);
            if (x == z && y == z) {
                graphPanel.add(node, x + (int) (Math.random() * dist / 3), y + (int) (Math.random() * dist / 3));
                nodePlacements[0] = dist;
                nodePlacements[1] = dist;
                nodePlacements[2] = z + dist;
            } else {
                if (x < z) {
                    graphPanel.add(node, x + (int) (Math.random() * dist / 3), z + (int) (Math.random() * dist / 3));
                    nodePlacements[0] = x + dist;
                } else if (y < z) {
                    graphPanel.add(node, z + (int) (Math.random() * dist / 3), y + (int) (Math.random() * dist / 3));
                    nodePlacements[1] = y + dist;
                }
            }
        }
        return node;
    }
}

class ViewerNode<T> extends JPanel {
    private static final long serialVersionUID = 1L;

    private List<ViewerNode<T>> connections;
    private JLabel nameLabel;

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
        g.fillOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
    }

    ViewerNode(final ViewerPanel displayPanel, T nodeName) {
        this.setLayout(new GridBagLayout());
        nameLabel = new JLabel(nodeName.toString());
        nameLabel.setVisible(true);
        connections = new ArrayList<>();

        // makes the nodes draggable
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getX() + getLocation().x, e.getY() + getLocation().y);
                displayPanel.repaint();
            }
        });

        setSize(10, 10);
        repaint();
    }

    public String getName() {
        return nameLabel.getText();
    }

    List<ViewerNode<T>> getConnections() {
        return connections;
    }

    void addConnection(ViewerNode<T> newConnection) {
        connections.add(newConnection);
        if (!newConnection.getConnections().contains(this)) {
            newConnection.addConnection(this);
        }
    }

    public void removeConnection(ViewerNode nodeToRemoveConnectionWith) {
        connections.remove(nodeToRemoveConnectionWith);
    }
}

class ViewerPanel<T> extends JPanel {
    private static final long serialVersionUID = 1L;

    private final List<ViewerNode<T>> theNodes = Collections.synchronizedList(new ArrayList<>());

    ViewerPanel() {
        setBackground(Color.white);
        repaint();
        setLayout(null);

        new Thread(() -> {
            while (true) {
                synchronized (theNodes) {
                    drawConnections();
                }
                try {
                    Thread.sleep(100);
                } catch (Exception ignored) {

                }
            }
        }).start();
    }

    void add(ViewerNode<T> newNode, int xPosition, int yPosition) {
        add(newNode);
        newNode.setLocation(xPosition, yPosition);
        newNode.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                newNode.setToolTipText(newNode.getName());
            }
        });
        theNodes.add(newNode);
    }

    private void drawConnections() {
        Graphics g = getGraphics();
        for (ViewerNode<T> currNode : theNodes) {
            for (ViewerNode<T> currConnectedNode : currNode.getConnections()) {
                g.drawLine(currNode.getLocation().x + 5, currNode.getLocation().y + 5,
                        currConnectedNode.getLocation().x + 5, currConnectedNode.getLocation().y + 5);
            }
        }
    }
}
