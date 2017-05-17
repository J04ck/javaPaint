package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.sun.org.apache.xml.internal.security.Init;

import paint.DrawingTools;

public class NetworkInterface {
	List<Client> myClients = new Vector<Client>();

	NetworkGui myGui;
	Thread myListenThread;
	Thread myHandlePacketsThread;
	boolean myRun = true;
	BufferedReader in = null;
	PrintWriter out = null;
	ServerSocket mySocket = new ServerSocket(41579);
	List<DrawingTools> myDrawingTools = new Vector<DrawingTools>();


	public NetworkInterface() throws UnknownHostException, IOException 
	{

	}

	public void run() {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				myGui =new NetworkGui(NetworkInterface.this);
			}
		});

		listen();
		handleIncoming();

	}

	private void listen()
	{
		myListenThread = new Thread(new Runnable()
		{
			@Override
			public void run() {
				Socket socket = null;
				while(myRun)
				{
					try{
						socket = mySocket.accept();
						Client client = new Client(socket);
						if(!client.myError)
						{
							/*
							if(!myDrawingTools.isEmpty())
							{
								for(int i = 0; i < myDrawingTools.size(); i++)
								{
									client.sendData(myDrawingTools.get(i));
								}
							}*/	
							myClients.add(client);	
							myGui.addClient(client);
						}									
					}
					catch (Exception e) {
						System.out.println(e);
					}
					Thread.yield();
				}				

			}			
		});
		myListenThread.start();			
	}	

	private void handleIncoming()
	{

		myHandlePacketsThread = new Thread(new Runnable()
		{
			@Override
			public void run() 
			{
				while(myRun)
				{
					
						for(Client a : myClients )
						{
							DrawingTools tool = a.getData();
							if(tool != null)
							{

								myDrawingTools.add(tool);
								handleOutGoing(a, tool);

							}
						}
						Thread.yield();

					
				}
			}			
		});
		myHandlePacketsThread.start();		

	}

	private void handleOutGoing(Client aClient, DrawingTools aTool)
	{		
		for(Client a : myClients )
		{
			if(aClient != a)
			{				
				a.sendData(aTool);
			}			
		}		
	}

	public void kickClient(Client aClient)
	{
		synchronized (myClients) {
			for(int i = 0; i < myClients.size(); i++)
			{
				if(myClients.get(i)== aClient)
				{
					myClients.remove(i);
					return;
				}
			}
		}
	}
}
