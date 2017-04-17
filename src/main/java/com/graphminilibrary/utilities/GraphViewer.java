package com.graphminilibrary.utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.graphminilibrary.graph.Graph;

/**
 * Displays the graph in the applet.
 */
public class GraphViewer {

	/**
	 * Display graph.
	 *
	 * @param graph the graph
	 */
	public static void displayGraph(Graph graph) {
		JFrame mainWindow = new JFrame("Graph Viewer");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(800, 800);
		mainWindow.setLayout(new GridLayout());

		ViewerPanel graphPanel = new ViewerPanel();
		mainWindow.add(graphPanel);

		mainWindow.setVisible(true);
		graphPanel.setVisible(true);

		Integer notesInLine = (int) (Math.ceil(Math.sqrt(graph.getAdjacencyListMap().keySet().size())) + 2);
		int dist = 800 / notesInLine;
		int[] nodePlacements = { dist, dist, dist, dist };

		HashMap<String, ViewerNode> created = new HashMap<>();

		for (String v : graph.getAdjacencyListMap().keySet()) {

			ViewerNode node = createNode(graphPanel, v, created, nodePlacements);

			for (String n : graph.getAdjacencyListMap().get(v)) {
				ViewerNode neighbor = createNode(graphPanel, n, created, nodePlacements);
				node.addConnection(neighbor);
			}
		}
	}

	/**
	 * Creates the node for viewer.
	 *
	 * @param graphPanel the graph panel
	 * @param name the node's name
	 * @param created the created
	 * @param nodePlacements the node placements
	 * @return the viewer node
	 */
	private static ViewerNode createNode(ViewerPanel graphPanel, String name, HashMap<String, ViewerNode> created,
			int[] nodePlacements) {

		int x = nodePlacements[0];
		int y = nodePlacements[1];
		int z = nodePlacements[2];
		int dist = nodePlacements[3];

		ViewerNode node = null;
		if (created.containsKey(name)) {
			node = created.get(name);
		} else {
			node = new ViewerNode(graphPanel, name);
			node.setVisible(true);
			created.put(name, node);
			if (x == z && y == z) {
				graphPanel.add(node, x + (int) (Math.random() * dist / 3), y + (int) (Math.random() * dist / 3));
				x = nodePlacements[0] = dist;
				y = nodePlacements[1] = dist;
				z = nodePlacements[2] = z + dist;
			} else {
				if (x < z) {
					graphPanel.add(node, x + (int) (Math.random() * dist / 3), z + (int) (Math.random() * dist / 3));
					x = nodePlacements[0] = x + dist;
				} else if (y < z) {
					graphPanel.add(node, z + (int) (Math.random() * dist / 3), y + (int) (Math.random() * dist / 3));
					y = nodePlacements[1] = y + dist;
				}
			}
		}
		return node;
	}
}

class ViewerNode extends JPanel {
	private static final long serialVersionUID = 1L;

	List<ViewerNode> connections;
	JLabel nameLabel;
	ViewerPanel displayPanel;

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
		g.fillOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
	}

	public ViewerNode(final ViewerPanel displayPanel, String nodeName) {
		this.setLayout(new GridBagLayout());
		this.displayPanel = displayPanel;
		nameLabel = new JLabel(nodeName);
		nameLabel.setVisible(true);
		connections = new ArrayList<ViewerNode>();

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

	public List<ViewerNode> getConnections() {
		return connections;
	}

	public void addConnection(ViewerNode newConnection) {
		connections.add(newConnection);
		if (newConnection.getConnections().contains(this) == false) {
			newConnection.addConnection(this);
		}
	}

	public void removeConnection(ViewerNode nodeToRemoveConnectionWith) {
		connections.remove(nodeToRemoveConnectionWith);
	}

}

class ViewerPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	List<ViewerNode> theNodes = Collections.synchronizedList(new ArrayList<>());

	public ViewerPanel() {
		setBackground(Color.white);
		repaint();
		setLayout(null);

		new Thread(new Runnable() {
			public void run() {
				while (true) {
					synchronized (theNodes) {
						drawConnections();
					}
					try {
						Thread.sleep(100);
					} catch (Exception e) {

					}
				}
			}
		}).start();
	}

	public void add(ViewerNode newNode, int xPosition, int yPosition) {
		add(newNode);
		newNode.setLocation(xPosition, yPosition);
		newNode.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				newNode.setToolTipText(newNode.getName());
			}
		});
		theNodes.add(newNode);
	}

	public void drawConnections() {
		Graphics g = getGraphics();
		for (ViewerNode currNode : theNodes) {
			for (ViewerNode currConnectedNode : currNode.getConnections()) {
				g.drawLine(currNode.getLocation().x + 5, currNode.getLocation().y + 5,
						currConnectedNode.getLocation().x + 5, currConnectedNode.getLocation().y + 5);
			}
		}
	}
}
