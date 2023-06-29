import java.awt.*;

public class EdgeData {
	private Double distance;
	private Color color = new Color(0, 0, 0);

	public EdgeData(Double distance) {
		this.distance = distance;
	}

	public String toString() {
		return distance.toString();
	}

	public double getDistance() {
		return distance;
	}

	public Color getColor() {
		return color;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
