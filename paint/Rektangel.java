package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;


public class Rektangel extends Rectangle implements DrawingTools {

	Color myColor;	
	
	@Override
	public void draw(Graphics g) {
		
		g.draw3DRect(x, y, width, height, false);
	}
	@Override
	public void setPosition(Point aPoint) {	
		if(x == 0 && y == 0)
		{
			x = (int) aPoint.getX();
			y = (int) aPoint.getY();
		}
		
		
		this.setFrame(this.x, this.y, aPoint.getX()-this.x, aPoint.getY()-this.y);
	}
	@Override
	public void setColor(Color aColor) {
		myColor = aColor;
		
	}
	@Override
	public Color getColor() 
	{
		
		return myColor;
	}
	

}
