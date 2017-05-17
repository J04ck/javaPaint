package paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics2D;


public class Freehand implements DrawingTools{

	
	List<Point> myLine = new ArrayList<Point>();
	Color myColor;
	
	public void setPosition(Point aPoint)
	{
		myLine.add(aPoint);
	}
	@Override
	public void draw(Graphics g) 
	{
		
		((Graphics2D)g).setStroke(new BasicStroke(3));
		
		for(int i = 0; i < myLine.size()-1; i++)
		{
			try {
				g.drawLine(myLine.get(i).x, myLine.get(i).y, myLine.get(i+1).x, myLine.get(i+1).y);
			} catch (Exception e) {
				e.getMessage();
				System.out.println(myLine);
			}
			
		}
		
	}
	@Override
	public void setColor(Color aColor) {
		myColor = aColor;
		
	}
	@Override
	public Color getColor() {
		
		return myColor;
	}
	@Override
	public boolean isEmpty() {
		return myLine.isEmpty();
		
	}

}
