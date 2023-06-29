import java.awt.*;

public class NodeData {
	private Point position;
	private String text;
	private Color color = new Color(0, 0, 0);

	public NodeData(Point position, String text) {
		this.position = position;
		this.text = text;
	}

	public Point getPosition() {
		return position;
	}

	public String getText() {
		return text;
	}

	public Color getColor() {
		return color;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return getText();
	}
}
