package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

public class NetworkGui extends JFrame  {
	
	
	DefaultListModel<Client> myModel = new DefaultListModel<>();
	JList myList = new JList(myModel);
	NetworkInterface myClient;
	public NetworkGui(NetworkInterface aClient)
	{
		super("Paint");
		myClient = aClient;
		
	
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Arkiv");
	
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Nytt");
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Disconnect all
			}
		});
		JMenuItem menuItem1 = new JMenuItem("Avsluta");	
		
		menuItem1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//disconnect all and exitGUI
			
			}
		});
		myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		myList.setLayoutOrientation(JList.VERTICAL_WRAP);
		myList.setVisibleRowCount(-1);
		myList.setPreferredSize(new Dimension(300, 200));
		add(myList, BorderLayout.CENTER);
		menu.add(menuItem);
		menu.add(menuItem1);
		setJMenuBar(menuBar);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(0,6));
		
		add(toppanel, BorderLayout.PAGE_START);
		JPanel bottompanel = new JPanel();
		JButton kick = new JButton("kick");
		kick.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					
				  Client client = (Client) myList.getSelectedValue();	
				  myClient.kickClient(client);
				  client.disconnect();
				  myModel.remove(myList.getSelectedIndex());
				
			}
		});

		bottompanel.add(kick);
		
		
		bottompanel.setPreferredSize(new Dimension(0, 50));
		add(bottompanel, BorderLayout.PAGE_END);
		pack();
			
	}
	
	void run()
	{
		
		
	}
	
	void refresh()
	{
		
	}
	

	void addClient(Client aClient)
	{
		myModel.addElement(aClient);
	}
}
