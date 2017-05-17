package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public interface DrawingTools extends Serializable {
	
	public void draw(Graphics g);
	public void setPosition(Point aPoint);
	public void setColor(Color aColor);
	public Color getColor();
	public boolean isEmpty();
	
}
