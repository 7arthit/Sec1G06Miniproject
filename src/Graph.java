
import java.util.*;

public class Graph<V, E> extends Object {

	private LinkedList<Edge> edges;
	private LinkedList<Node> nodes;

	public Graph() {
		edges = new LinkedList<Edge>();
		nodes = new LinkedList<Node>();
	}

	public Node closest(HashSet<Node> unvisited, HashMap<Node, Double> dist) {
		double min = Double.MAX_VALUE;
		Node closestNode = null;
		for (Node node : unvisited) {
			if (dist.get(node) <= min) {
				min = dist.get(node);
				closestNode = node;
			}
		}
		return closestNode;
	}

	public HashMap<Node, Double> distances(Node start) {
		HashMap<Node, Double> dist = new HashMap<Node, Double>(numNodes());
		for (Node node : nodes) {
			dist.put(node, Double.MAX_VALUE);
		}
		dist.put(start, 0.0);

		HashSet<Node> unvisited = new HashSet<Node>();
		unvisited.addAll(getNode());

		while (!unvisited.isEmpty()) {
			Node node = closest(unvisited, dist);
			try {
				for (Edge edge : node.getEdgeOut()) {
					Node neighbor = edge.getTail();
					double temp = dist.get(node) + Double.valueOf(edge.getData().toString());
					if (temp < dist.get(neighbor)) {
						dist.put(neighbor, temp);
					}

				}
			} catch (NullPointerException e) {
			}
			unvisited.remove(node);
		}
		return dist;
	}

	public LinkedList<Edge> distances(Node start, Node end) {
		// Initial set up
		HashMap<Node, Double> dist = new HashMap<Node, Double>(numNodes());
		HashMap<Node, Edge> predecessors = new HashMap<Node, Edge>(numNodes());
		for (Node node : nodes) {
			dist.put(node, Double.MAX_VALUE);
		}
		dist.put(start, 0.0);
		predecessors.put(start, null);

		// Calculate the distances
		HashSet<Node> unvisited = new HashSet<Node>();
		unvisited.addAll(getNode());

		while (!unvisited.isEmpty()) {
			Node node = closest(unvisited, dist);
			try {
				for (Edge edge : node.getEdgeOut()) {
					Node neighbor = edge.getTail();
					double temp = dist.get(node) + Double.valueOf(edge.getData().toString());
					if (temp < dist.get(neighbor)) {
						dist.put(neighbor, temp);
						predecessors.put(neighbor, edge);
					}
				}
			} catch (NullPointerException e) {
			}
			unvisited.remove(node);
		}

		LinkedList<Edge> path = new LinkedList<Edge>();
		Node current = end;
		Edge edge = predecessors.get(current);
		while (!current.equals(start) && edge != null) {
			path.addFirst(edge);
			current = edge.getHead();
			edge = predecessors.get(current);
		}
		return path;
	}

	public HashSet<Node> heads(HashSet<Edge> edges) {
		HashSet<Node> headNodes = new HashSet<Node>();
		for (Edge edge : edges) {
			headNodes.add(edge.getHead());
		}
		return headNodes;
	}

	public HashSet<Node> tails(HashSet<Edge> edges) {
		HashSet<Node> tailNodes = new HashSet<Node>();
		for (Edge edge : edges) {
			tailNodes.add(edge.getTail());
		}
		return tailNodes;
	}

	public Node getNode(int i) {
		return nodes.get(i);
	}

	public LinkedList<Node> getNode() {
		return nodes;
	}

	public Edge getEdge(int i) {
		return edges.get(i);
	}

	public LinkedList<Edge> getEdge() {
		return edges;
	}

	public Edge getEdgeRef(Node head, Node tail) {
		return head.edgeTo(tail);
	}

	public int numNodes() {
		return nodes.size();
	}

	public int numEdges() {
		return edges.size();
	}

	public Node addNode(V data) {
		Node newNode = new Node(data);
		nodes.add(newNode);
		return newNode;
	}

	public Edge addEdge(E data, Node head, Node tail) {
		if (head.edgeTo(tail) == null) {
			Edge newEdge = new Edge(data, head, tail);
			head.addEdgeOut(newEdge);
			tail.addEdgeIn(newEdge);
			edges.add(newEdge);
			return newEdge;
		} else {
			return null;
		}
	}

	public HashSet<Node> otherNodes(HashSet<Node> group) {
		HashSet<Node> others = new HashSet<Node>(nodes);
		others.removeAll(group);
		return others;
	}

	public void print() {
		for (Node node : nodes) {
			System.out.println(node.getData() + ":");
			for (Edge edge : node.getEdgeOut()) {
				System.out.println(edge.getHead().getData() + "--" + edge.getData() + "-->" + edge.getTail().getData());
			}
			for (Edge edge : node.getEdgeIn()) {
				System.out.println(edge.getHead().getData() + "--" + edge.getData() + "-->" + edge.getTail().getData());
			}
			System.out.println();
		}

		System.out.println("AllEdges:");
		for (Edge edge : edges) {
			System.out.println(edge.getHead().getData() + "--" + edge.getData() + "-->" + edge.getTail().getData());
		}
	}

	public void removeEdge(Edge edge) {
		edge.getHead().removeEdgeRef(edge);
		edge.getTail().removeEdgeRef(edge);
		edges.remove(edge);
	}

	public void removeEdge(Node head, Node tail) {
		Edge removedEdge = head.edgeTo(tail);
		removeEdge(removedEdge);
	}

	public void removeNode(Node node) {
		while (!(node.getEdgeOut().isEmpty())) {
			Edge edge = node.getEdgeOut().get(0);
			removeEdge(edge);
		}
		while (!(node.getEdgeIn().isEmpty())) {
			Edge edge = node.getEdgeIn().get(0);
			removeEdge(edge);
		}
		nodes.remove(node);
	}

	public class Edge {

		private E data;
		private Node head;
		private Node tail;

		public Edge(E data, Node head, Node tail) {
			this.data = data;
			this.head = head;
			this.tail = tail;
		}

		public boolean equals(Object o) {
			boolean result = false;
			if (o.getClass() == getClass()) {
				@SuppressWarnings("unchecked")
				Edge edge = (Edge) o;
				if (edge.getHead() == head && edge.getTail() == tail) {
					result = true;
				}
			}
			return result;
		}

		public E getData() {
			return data;
		}

		public Node getHead() {
			return head;
		}

		public Node getTail() {
			return tail;
		}

		public int hashCode() {
			int hash = 17;
			hash = hash * 31 + head.hashCode();
			hash = hash * 31 + tail.hashCode();
			return hash;
		}

		public Node oppositeTo(Node node) {
			// not sure
			if (node == head) {
				return tail;
			} else if (node == tail) {
				return head;
			} else {
				return null;
			}
		}

		public void setData(E data) {
			this.data = data;
		}

	}

	public class Node {
		private V data;
		private LinkedList<Edge> edgeRefOut;
		private LinkedList<Edge> edgeRefIn;
		public boolean temp_visited;

		public Node(V data) {
			this.data = data;
			edgeRefOut = new LinkedList<Edge>();
			edgeRefIn = new LinkedList<Edge>();
			temp_visited = false;
		}

		private void addEdgeOut(Edge edge) {
			edgeRefOut.add(edge);
		}

		private void addEdgeIn(Edge edge) {
			edgeRefIn.add(edge);
		}

		public Edge edgeTo(Node neighbor) {
			Edge result = null;
			Edge possibleEdge = new Edge(null, this, neighbor);
			for (Edge edge : edgeRefOut) {
				if (edge.equals(possibleEdge)) {
					result = edge;
					break;
				}
			}
			if (result == null) {
				for (Edge edge : edgeRefIn) {
					if (edge.equals(possibleEdge)) {
						result = edge;
						break;
					}
				}
			}
			return result;
		}

		public V getData() {
			return data;
		}

		public LinkedList<Edge> getEdgeOut() {
			return edgeRefOut;
		}

		public LinkedList<Edge> getEdgeIn() {
			return edgeRefIn;
		}

		public HashSet<Node> getNeighborOut() {
			HashSet<Node> neighborOut = new HashSet<Node>();
			for (Edge edge : edgeRefOut) {
				neighborOut.add(edge.getTail());
			}
			return neighborOut;
		}

		public HashSet<Node> getNeighborIn() {
			HashSet<Node> neighborIn = new HashSet<Node>();
			for (Edge edge : edgeRefIn) {
				neighborIn.add(edge.getHead());
			}
			return neighborIn;
		}

		public boolean isNeighbor(Node node) {
			return node.getNeighborOut().contains(node) || node.getNeighborIn().contains(node);
		}

		private void removeEdgeRef(Edge edge) {
			if (!edgeRefOut.remove(edge)) {
				edgeRefIn.remove(edge);
			}
		}

		public void setData(V data) {
			this.data = data;
		}

	}
}