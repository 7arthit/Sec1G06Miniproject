
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

public class GraphGUI {
	private GraphCanvas canvas;
	private JLabel instr;
	private JButton addNodeButton;
	private JButton rmvNodeButton;
	private JButton addEdgeButton;
	private JButton rmvEdgeButton;
	private JButton chgTextButton;
	private JButton chgDistButton;
	private JButton spButton;
	private JButton spaButton;
	private JButton rfButton;
	private JButton btnHelp;

	private InputMode mode = InputMode.ADD_NODES;
	private Graph<NodeData, EdgeData>.Node nodeUnderMouse;

	public static void main(String[] args) {
		final GraphGUI GUI = new GraphGUI();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI.createAndShowGUI();
			}
		});
	}

	public void createAndShowGUI() {
		JFrame.setDefaultLookAndFeelDecorated(false);
		JFrame frame = new JFrame("Sec1G06Miniproject");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createComponents(frame);
		frame.pack();
		frame.setVisible(true);
	}

	public void createComponents(JFrame frame) {
		Container pane = frame.getContentPane();
		pane.setLayout(new FlowLayout());
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		canvas = new GraphCanvas();
		GraphMouseListener gml = new GraphMouseListener();
		canvas.addMouseListener(gml);
		canvas.addMouseMotionListener(gml);
		panel1.add(canvas);
		instr = new JLabel("Click to add new nodes; drag to move.");
		panel1.add(instr, BorderLayout.NORTH);
		pane.add(panel1);

		// build graph buttons
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(12, 1));

		btnHelp = new JButton("Description Nodes");
		panel2.add(btnHelp);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeMouseAdapters();
				GraphGUI.Help help = new Help();
			}

			private void removeMouseAdapters() {

			}
		});

		addNodeButton = new JButton("Add/Move Nodes");
//		addNodeButton.setBackground(Color.BLACK);
//		addNodeButton.setForeground(Color.WHITE);
		addNodeButton.addActionListener(new AddNodeListener());

		rmvNodeButton = new JButton("Remove Nodes");
//		rmvNodeButton.setBackground(new Color(0,0,0));
//		rmvNodeButton.setForeground(Color.WHITE);
		rmvNodeButton.addActionListener(new RmvNodeListener());

		addEdgeButton = new JButton("Add Edges");
//		addEdgeButton.setBackground(Color.RED);
//		addEdgeButton.setForeground(Color.WHITE);
		addEdgeButton.addActionListener(new AddEdgeListener());

		rmvEdgeButton = new JButton("Remove Edges");
//		rmvEdgeButton.setBackground(Color.BLUE);
//		rmvEdgeButton.setForeground(Color.WHITE);
		rmvEdgeButton.addActionListener(new RmvEdgeListener());

		chgTextButton = new JButton("Change Text");
//		chgTextButton.setBackground(Color.BLACK);
//		chgTextButton.setForeground(Color.WHITE);
		chgTextButton.addActionListener(new ChgTextListener());

		chgDistButton = new JButton("Change Distance");
//		chgDistButton.setBackground(Color.BLACK);
//		chgDistButton.setForeground(Color.WHITE);
		chgDistButton.addActionListener(new ChgDistListener());

		spButton = new JButton("Shortest Path Step");
//		spButton.setBackground(Color.BLACK);
//		spButton.setForeground(Color.WHITE);
		spButton.addActionListener(new SPListener());

		spaButton = new JButton("Shortest Path All");
//		spaButton.setBackground(Color.BLACK);
//		spaButton.setForeground(Color.WHITE);
		spaButton.addActionListener(new SPAListener());

		rfButton = new JButton("Refresh");
//		rfButton.setBackground(Color.PINK);
//		rfButton.setForeground(Color.WHITE);
		rfButton.addActionListener(new RFListener());

		panel2.add(addNodeButton);
		panel2.add(rmvNodeButton);
		panel2.add(addEdgeButton);
		panel2.add(rmvEdgeButton);
		panel2.add(chgTextButton);
		panel2.add(chgDistButton);
		panel2.add(spButton);
		panel2.add(spaButton);
		panel2.add(rfButton);

		pane.add(panel2);
	}

	@SuppressWarnings("unchecked")
	public Graph<NodeData, EdgeData>.Node findNearbyNode(int x, int y) {
		@SuppressWarnings("rawtypes")
		Graph.Node nearbyNode = null;
		for (Graph<NodeData, EdgeData>.Node node : canvas.graph.getNode()) {
			Point p = node.getData().getPosition();
			if (p.distance(x, y) <= 40) {
				nearbyNode = node;
			}
		}
		return nearbyNode;
	}

	enum InputMode {
		ADD_NODES, RMV_NODES, ADD_EDGES, RMV_EDGES, CHG_TEXT, CHG_DIST, S_PATH, S_PATHA
	}

	private class AddNodeListener implements ActionListener {
		/** Event handler for AddPoint button */
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.ADD_NODES;
			instr.setText("Click to add new nodes or change their location.");
		}
	}

	private class RmvNodeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.RMV_NODES;
			instr.setText("Drag on nodes to remove them.");
		}
	}

	private class AddEdgeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.ADD_EDGES;
			instr.setText("Drag from one node to another to add an edge.");
		}
	}

	private class RmvEdgeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.RMV_EDGES;
			instr.setText("Drag from one node to another to remove an edge.");
		}
	}

	private class ChgTextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.CHG_TEXT;
			instr.setText("Click one node to change the text on the node.");
		}
	}

	private class ChgDistListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.CHG_DIST;
			instr.setText("Drag from one node to another to change the distance on the edge.");
		}
	}

	private class SPListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.S_PATH;
			instr.setText("Drag from one node to another to find their shortest path.");
		}
	}

	private class SPAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mode = InputMode.S_PATHA;
			instr.setText("Drag from one node to another to find their shortest path.");
		}
	}

	private class RFListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			canvas.refresh();
			btnHelp.setEnabled(true);
			addNodeButton.setEnabled(true);
			rmvNodeButton.setEnabled(true);
			addEdgeButton.setEnabled(true);
			rmvEdgeButton.setEnabled(true);
			chgTextButton.setEnabled(true);
			chgDistButton.setEnabled(true);
			spButton.setEnabled(true);
			spaButton.setEnabled(true);
			instr.setText("Try functions by clicking buttons.");
		}
	}

	private class GraphMouseListener extends MouseAdapter implements MouseMotionListener {
		@SuppressWarnings("incomplete-switch")
		public void mouseClicked(MouseEvent e) {
			Graph<NodeData, EdgeData>.Node nearbyNode = findNearbyNode(e.getX(), e.getY());
			boolean work = false;
			switch (mode) {
			case ADD_NODES:
				if (nearbyNode == null) {
					char c = (char) (canvas.graph.numNodes() % 26 + 65);
					canvas.graph.addNode(new NodeData(e.getPoint(), Character.toString(c)));
					canvas.repaint();
					work = true;
				}
				if (!work) {
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			case RMV_NODES:
				if (nearbyNode != null) {
					canvas.graph.removeNode(nearbyNode);
					canvas.repaint();
					work = true;
				}
				if (!work) {
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			case CHG_TEXT:
				if (nearbyNode != null) {
					while (!work) {
						try {
							JFrame frame = new JFrame("Enter a text");
							String text = JOptionPane.showInputDialog(frame, "Please enter the text on this node.");
							if (text != null) {
								nearbyNode.getData().setText(text);
								canvas.repaint();
								work = true;
							} else {
								Toolkit.getDefaultToolkit().beep();
							}
						} catch (Exception exception) {
						}
					}
				}
			}
		}

		public void mousePressed(MouseEvent e) {
			nodeUnderMouse = findNearbyNode(e.getX(), e.getY());
		}

		@SuppressWarnings({ "unchecked", "incomplete-switch" })
		public void mouseReleased(MouseEvent e) {
			Graph<NodeData, EdgeData>.Node nearbyNode = findNearbyNode(e.getX(), e.getY());
			boolean work = false;
			switch (mode) {
			case ADD_EDGES:
				if (nodeUnderMouse != null && nearbyNode != null && nearbyNode != nodeUnderMouse) {
					canvas.graph.addEdge((new EdgeData(-1.0)), nodeUnderMouse, nearbyNode);
					canvas.repaint();
					work = true;
				}
				if (!work) {
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			case RMV_EDGES:
				if (nodeUnderMouse != null) {
					@SuppressWarnings("rawtypes")
					Graph.Edge edge = nodeUnderMouse.edgeTo(nearbyNode);
					if (edge != null) {
						canvas.graph.removeEdge(edge);
						canvas.repaint();
						work = true;
					}
				}
				if (!work) {
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			case S_PATH:
				if (nodeUnderMouse != null && nearbyNode != null && nodeUnderMouse != nearbyNode) {
					LinkedList<Graph<NodeData, EdgeData>.Edge> path = canvas.graph.distances(nodeUnderMouse,
							nearbyNode);
					(new TraversalThread(path)).execute();
					String x = "", xx = "";
					int cnt = 0;
					if (path != null && !path.isEmpty()) {
						System.out.print("Path: ");
						for (Graph<NodeData, EdgeData>.Edge edge : path) {
							x = (String) (edge.getHead().getData() + ", " + edge.getData() + ", "
									+ edge.getTail().getData());
							System.out.print(x);
							if (cnt == 0) {
								xx += x;
								cnt++;
							} else {
								xx += x.substring(1);
							}
						}
						instr.setText(" Path " + nodeUnderMouse.getData().getText() + ", "
								+ nearbyNode.getData().getText() + " = "
								+ canvas.graph.distances(nodeUnderMouse).get(nearbyNode) + " mater. Path: " + xx);
					}
					work = true;
				}
				if (!work) {
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			case S_PATHA:
				if (nodeUnderMouse != null && nearbyNode != null && nodeUnderMouse != nearbyNode) {
					LinkedList<Graph<NodeData, EdgeData>.Edge> path = canvas.graph.distances(nodeUnderMouse,
							nearbyNode);
					(new TraversalThreadAll(path)).execute();
					String x = "", xx = "";
					int cnt = 0;
					if (path != null && !path.isEmpty()) {
						System.out.print("Path: ");
						for (Graph<NodeData, EdgeData>.Edge edge : path) {
							x = (String) (edge.getHead().getData() + ", " + edge.getData() + ", "
									+ edge.getTail().getData());
							System.out.print(x);
							if (cnt == 0) {
								xx += x;
								cnt++;
							} else {
								xx += x.substring(1);
							}
						}
						instr.setText(" Path " + nodeUnderMouse.getData().getText() + ", "
								+ nearbyNode.getData().getText() + " = "
								+ canvas.graph.distances(nodeUnderMouse).get(nearbyNode) + " mater. Path: " + xx);
					}
					work = true;
				}
				if (!work) {
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			case CHG_DIST:
				if (nodeUnderMouse != null && nearbyNode != null) {
					Graph<NodeData, EdgeData>.Edge edge = nodeUnderMouse.edgeTo(nearbyNode);
					if (edge != null) {
						while (!work) {
							try {
								JFrame frame = new JFrame("Enter a distance");
								String distance = JOptionPane.showInputDialog(frame,
										"Please enter the distance represented by this edge.");
								edge.getData().setDistance(Double.valueOf(distance));
								canvas.repaint();
								work = true;
							} catch (Exception exception) {
								// do nothing
							}
						}
					}
				}
				if (!work) {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (mode == InputMode.ADD_NODES && nodeUnderMouse != null && e.getX() >= 30 && e.getY() >= 30
					&& e.getX() <= 1150 && e.getY() <= 670) {
				nodeUnderMouse.getData().setPosition(e.getPoint());
				canvas.repaint();
			}
		}

		public void mouseMoved(MouseEvent e) {
			nodeUnderMouse = null;
		}
	}

	protected class Help {
		JFrame frame;
		JTextArea jLabel;
		JButton btn;
		JPanel panel;

		public Help() {
			this.gui();
		}

		public void gui() {
			frame = new JFrame("Description Nodes");
			frame.setVisible(true);
			frame.setSize(400, 500);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			panel = new JPanel();
			panel.setSize(frame.getSize());
			panel.setBackground(Color.WHITE);

			jLabel = new JTextArea();
			jLabel.setBounds(20, 20, 56, 360);
			jLabel.setEditable(false);

			btn = new JButton("OK");
			btn.setBounds(155, 400, 70, 20);

			panel.add(jLabel);
			frame.add(btn);

			jLabel.setText("\n" + "( A ) Art and Cultur Management MSU\n" + "( B ) Mahasarakham Business School\n"
					+ "( C ) Faculty of Science MSU\n" + "( D ) College of Politics and Governance MSU\n"
					+ "( E ) Faculty of Public Health MSU\n" + "( F ) Information Techology MSU\n"
					+ "( G ) Engineering building MSU\n" + "( H ) Faculty of Nursing MSU\n"
					+ "( I ) Faculty of Pharmacy MSU\n" + "( J ) Meseum MSU\n" + "( K ) MSU Outlet\n"
					+ "( L ) Faculty of Tourism and hotel management MSU\n" + "( M ) Parichart Hotel MSU\n"
					+ "( N ) Chaloem Phrakiat Convention Hall MSU\n" + "( O ) Health green garden MSU\n"
					+ "( P ) BMC MSU\n" + "( Q ) Faculty of Veterinary Science MSU\n"
					+ "( R ) Faculty of Medicine MSU\n" + "( S ) Faculty of Education MSU\n"
					+ "( T ) Suddhavej Hospital MSU");

			frame.add(panel);
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
		}

	}

	private class TraversalThread extends SwingWorker<Boolean, Object> {
		/** The path that needs to paint */
		private LinkedList<Graph<NodeData, EdgeData>.Edge> path;

		/** The Constructor of TraversalThread */
		public TraversalThread(LinkedList<Graph<NodeData, EdgeData>.Edge> path) {
			this.path = path;
		}

		@Override
		public Boolean doInBackground() {
			btnHelp.setEnabled(true);
			addNodeButton.setEnabled(false);
			rmvNodeButton.setEnabled(false);
			addEdgeButton.setEnabled(false);
			rmvEdgeButton.setEnabled(false);
			chgTextButton.setEnabled(false);
			chgDistButton.setEnabled(false);
			spButton.setEnabled(false);
			spaButton.setEnabled(false);
			rfButton.setEnabled(false);
			return canvas.paintTraversal(path);
		}

		@Override
		protected void done() {
			try {
				if (path.isEmpty() && path != null) {
					instr.setText("There is no path. Please refresh.");
				}
				rfButton.setEnabled(true);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class TraversalThreadAll extends SwingWorker<Boolean, Object> {
		/** The path that needs to paint */
		private LinkedList<Graph<NodeData, EdgeData>.Edge> path;

		/** The Constructor of TraversalThread */
		public TraversalThreadAll(LinkedList<Graph<NodeData, EdgeData>.Edge> path) {
			this.path = path;
		}

		@Override
		public Boolean doInBackground() {
			btnHelp.setEnabled(true);
			addNodeButton.setEnabled(false);
			rmvNodeButton.setEnabled(false);
			addEdgeButton.setEnabled(false);
			rmvEdgeButton.setEnabled(false);
			chgTextButton.setEnabled(false);
			chgDistButton.setEnabled(false);
			spButton.setEnabled(false);
			spaButton.setEnabled(false);
			rfButton.setEnabled(false);
			return canvas.paintTraversalAll(path);
		}

		@Override
		protected void done() {
			try {
				if (path.isEmpty() && path != null) {
					instr.setText("There is no path. Please refresh.");
				}
				rfButton.setEnabled(true);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}