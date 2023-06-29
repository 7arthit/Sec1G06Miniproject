
import java.awt.*;
import java.util.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GraphCanvas extends JComponent {

	public Graph<NodeData, EdgeData> graph;

	public GraphCanvas() {
		graph = new Graph<NodeData, EdgeData>();
	}

	public void paintComponent(Graphics g) {
		// paint edges
		for (Graph<NodeData, EdgeData>.Edge edge : graph.getEdge()) {

			g.setColor(edge.getData().getColor());

			Point h = edge.getHead().getData().getPosition();
			Point t = edge.getTail().getData().getPosition();

			double dist = paintLine(g, (int) h.getX(), (int) h.getY(), (int) t.getX(), (int) t.getY(), 20, 10,
					edge.getData().getDistance());
			if (edge.getData().getDistance() == -1) {
				edge.getData().setDistance(dist);
			}
		}

		// paint nodes
		for (Graph<NodeData, EdgeData>.Node node : graph.getNode()) {
			Point p = node.getData().getPosition();

			g.setColor(node.getData().getColor());
			g.fillOval((int) p.getX() - 20, (int) p.getY() - 20, 40, 40);
			// paint text
			g.setColor(Color.white);
			g.drawString(node.getData().getText(), (int) p.getX() - 5, (int) p.getY() + 5);
		}
	}

	private double paintLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h, Double dist) {
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double sin = dy / D, cos = dx / D;

		x2 = (int) (x2 - 20 * cos);
		y2 = (int) (y2 - 20 * sin);
		D = D - 0;
		double xm = D - d, xn = xm, ym = h, yn = -h, x;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };

		g.drawLine(x1, y1, x2, y2);
		g.fillPolygon(xpoints, ypoints, 3);
		if (dist == -1.0) {
			dist = Math.round((D + 40) * 100) / 100.00;
		}
		g.drawString(Double.toString(dist), (int) (xm - 30 * cos), (int) (ym - 30 * sin));
		return dist;
	}

	// traversal
	public Boolean paintTraversal(LinkedList<Graph<NodeData, EdgeData>.Edge> path) {
		boolean painting;
		if (path.isEmpty()) {
			painting = false;
			return painting;
		} else {
			painting = true;
		}
		for (Graph<NodeData, EdgeData>.Edge edge : graph.getEdge()) {
			edge.getData().setColor(Color.blue);
		}
		for (Graph<NodeData, EdgeData>.Node node : graph.getNode()) {
			node.getData().setColor(Color.lightGray);
		}
		path.get(0).getHead().getData().setColor(Color.ORANGE);
		repaint();
//        if() {}
		for (Graph<NodeData, EdgeData>.Edge edge : path) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException ignore) {
			}
			// Color the current edge
			edge.getData().setColor(new Color(255, 0, 0));
			edge.getTail().getData().setColor(Color.GREEN);

			repaint();
			try {
				Thread.sleep(800);
			} catch (InterruptedException ignore) {
			}
			edge.getTail().getData().setColor(new Color(255, 0, 0));
			repaint();
		}
		return painting;
	}

	public Boolean paintTraversalAll(LinkedList<Graph<NodeData, EdgeData>.Edge> path) {
		boolean painting;
		if (path.isEmpty()) {
			painting = false;
			return painting;
		} else {
			painting = true;
		}
		for (Graph<NodeData, EdgeData>.Edge edge : graph.getEdge()) {
			edge.getData().setColor(Color.blue);
		}
		for (Graph<NodeData, EdgeData>.Node node : graph.getNode()) {
			node.getData().setColor(Color.lightGray);
		}
		path.get(0).getHead().getData().setColor(Color.ORANGE);
		repaint();
//        if() {}
		for (Graph<NodeData, EdgeData>.Edge edge : path) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ignore) {
			}
			// Color the current edge
			edge.getData().setColor(new Color(255, 0, 0));
			edge.getTail().getData().setColor(Color.GREEN);

			repaint();
			try {
				Thread.sleep(1);
			} catch (InterruptedException ignore) {
			}
			edge.getTail().getData().setColor(new Color(255, 0, 0));
			repaint();
		}
		return painting;
	}

	// refresh
	public void refresh() {
		for (Graph<NodeData, EdgeData>.Edge edge : graph.getEdge()) {
			edge.getData().setColor(new Color(0, 0, 0));
		}
		for (Graph<NodeData, EdgeData>.Node node : graph.getNode()) {
			node.getData().setColor(new Color(0, 0, 0));
		}
		repaint();
	}

	public Dimension getMinimumSize() {
		return new Dimension(1200, 700);
	}

	public Dimension getPreferredSize() {
		return new Dimension(1200, 700);
	}

}