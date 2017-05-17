package paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;



public class Gui extends JFrame {
	
	List<Color> myColors = Arrays.asList(Color.RED, Color.BLACK, Color.YELLOW, Color.GREEN, Color.BLUE);
	String[] myEvents = {"Rektangel", "Frihand"};
	//List<DrawingTools> myTools = new ArrayList<DrawingTools>();
	
	Color myColor= new Color(0, 0, 0);
	PaintBox myPaintBox = new PaintBox();
	JTextField leftText = new JTextField();
	boolean myTool = false;
	public boolean userInput = false;

	
	public Gui()
	{
		super("Paint");
		
	
			
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Arkiv");
	
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Nytt");
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				myPaintBox.clearPaintBox();
				myPaintBox.repaint();
			}
		});
		JMenuItem menuItem1 = new JMenuItem("Avsluta");	
		
		menuItem1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				exitGui();
			}
		});
		
		menu.add(menuItem);
		menu.add(menuItem1);
		setJMenuBar(menuBar);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		
		
		
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(0,6));
		
		add(toppanel, BorderLayout.PAGE_START);
	
		JPanel bottompanel = new JPanel();
		bottompanel.setLayout(new BoxLayout(bottompanel, BoxLayout.X_AXIS));
		leftText = new JTextField("Kordinater: 000,000");
		leftText.setBorder(BorderFactory.createEmptyBorder());
		leftText.setAlignmentX(LEFT_ALIGNMENT);
		bottompanel.add(leftText);
		myPaintBox.setBottomRightTextField(leftText);
		
		
		JTextField rightText = new JTextField("Färgval: ");
		rightText.setMaximumSize(new Dimension(100, 50));
		
		rightText.setBorder(BorderFactory.createEmptyBorder());
		rightText.setAlignmentX(RIGHT_ALIGNMENT);
		bottompanel.add(rightText);
		JPanel bottomColor = new JPanel();
		bottomColor.setPreferredSize(new Dimension(100,50));
		bottomColor.setMaximumSize(new Dimension(100,50));
		bottomColor.setBorder(BorderFactory.createEmptyBorder());
		bottomColor.setBackground(myColors.get(2));
		bottompanel.add(bottomColor);
		bottomColor.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(bottompanel,BorderLayout.SOUTH);
	
		add(myPaintBox, BorderLayout.CENTER);
		
		for (int i = 0; i < myColors.size(); i++)
		{
			JPanel colorpanel = new JPanel();
			colorpanel.setPreferredSize(new Dimension(100, 50));
			colorpanel.setVisible(true);
			colorpanel.setBackground(myColors.get(i));
			colorpanel.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mousePressed(MouseEvent e) {
					bottomColor.setBackground(colorpanel.getBackground());
					myColor = bottomColor.getBackground();
					myPaintBox.setMyColor(myColor);
				}

			});
			
			toppanel.add(colorpanel);
		}

		JComboBox<String> boxcombo = new JComboBox<String>(myEvents);
		toppanel.add(boxcombo);
		boxcombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				myPaintBox.setMyTools(!myTool);
				myTool = !myTool;
				
			}
		});
		pack();
			
	}

	public Queue<DrawingTools> getQueue()
	{
	
		return myPaintBox.mySendQueue;
	}
	
	void exitGui()
	{
		System.exit(0);
	}
	
	public void addDrawing(DrawingTools aTool)
	{
		myPaintBox.addDrawing(aTool);
		
	}
	

}


