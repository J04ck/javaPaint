package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.JPanel;
import javax.swing.JTextField;

import server.NetworkInterface;



public class PaintBox extends JPanel  
{
	private class MyMouseAdapter extends MouseAdapter
	{

		@Override
		public void mousePressed(MouseEvent e) 
		{
			Point a = new Point(e.getX(), e.getY());
			myTools.setPosition(a);

		}

		@Override
		public void mouseDragged(MouseEvent e) {

			if(e.getPoint() != null)
			{
				myTools.setPosition(e.getPoint());
				repaint();
			}

		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			mySendQueue.add(myTools);
			myPaintings.add(myTools);
			setMyTools(myToolindex);
			
		}

		@Override
		public void mouseExited(MouseEvent e)
		{

			if(!myTools.isEmpty())
			{
				mySendQueue.add(myTools);
				myPaintings.add(myTools);
				setMyTools(myToolindex);

			}

		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			myCordinates.setText(""+e.getPoint().x + " " +e.getPoint().y);
		}
	}

	Color myColor =  Color.YELLOW;
	DrawingTools myTools;
	Point myPoint = null;
	List<DrawingTools> myPaintings = new ArrayList<DrawingTools>();
	MyMouseAdapter myAdapter;
	JTextField myCordinates;
	boolean myToolindex;
	Queue<DrawingTools> mySendQueue = new LinkedList<DrawingTools>();
	
	public PaintBox()
	{
		setBackground(new Color(255, 255, 255));

		Thread listnerThread = new Thread(new Runnable()
		{

			public void run()
			{
				try {

					myAdapter= new MyMouseAdapter();
					addMouseMotionListener(myAdapter);
					addMouseListener(myAdapter);
					myTools = new Rektangel();
				} catch (Exception e) {
					System.out.println(e);
				}

			}
		});
		listnerThread.start();


	}


	public void clearPaintBox()
	{
		myPaintings.clear();
	}

	public void setMyTools(Boolean index)
	{
		myToolindex  = index;
		if(index)
		{
			myTools = new Freehand();
		}
		else 
		{
			myTools = new Rektangel();
		}

	}


	public void setMyColor(Color aColor) {
		this.myColor = aColor;
	}

	public void setBottomRightTextField(JTextField aTextField)
	{
		myCordinates = aTextField;
	}

	public void setPoint(Point aPoint)
	{
		myPoint = aPoint;
	}

	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);

		for(DrawingTools a : myPaintings )
		{
			arg0.setColor(a.getColor());
			a.draw(arg0);
		}

		arg0.setColor(myColor);
		myTools.setColor(myColor);
		myTools.draw(arg0);	
	}
	
	public void addDrawing(DrawingTools aTool)
	{
		myPaintings.add(aTool);
		
	}

}
