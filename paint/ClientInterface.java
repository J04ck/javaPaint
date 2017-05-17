package paint;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import server.NetworkGui;
import server.NetworkInterface;

public class ClientInterface {

	private class GetInput
	{
		public void  getInput()
		{
			JTextField name = new JTextField(10);
			JTextField ip = new JTextField(15);
			JTextField port = new JTextField(5);

			JPanel myPanel = new JPanel();
			myPanel.add(new JLabel("Name:"));
			myPanel.add(name);
			myPanel.add(Box.createVerticalStrut(15));
			myPanel.add(new JLabel("Ip:"));
			myPanel.add(ip);
			myPanel.add(Box.createVerticalStrut(15)); // a spacer
			myPanel.add(new JLabel("Port:"));
			myPanel.add(port);

			int result = JOptionPane.showConfirmDialog(null, myPanel, "Enter Connection information", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) 
			{
				myName=name.getText();
				myIp =  ip.getText();
				try {
					myPort =Integer.parseInt(port.getText());
				} catch (Exception e) 
				{
					e.printStackTrace();
				}

			}
		}
	}

	Socket mySocket;
	Gui myGui;
	GetInput myGetter = new GetInput();;

	ObjectInputStream myInput;
	ObjectOutputStream myOutput; 
	int myPort = 41579;
	String myIp= "127.0.0.1";
	String myName = "test";
	boolean myRun = true;
	Thread packetSenderThread;
	Thread packetReceiverThread;
	ClientInterface()
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				myGui = new Gui();
			}
		});

		try
		{

			myGetter.getInput(); // Ugly i want to wait for EDT Thread but buhuuu , going to ask the teacher 

			mySocket = new Socket(myIp, myPort);

			myOutput = new ObjectOutputStream(mySocket.getOutputStream());
			myOutput.writeObject(myName);
			myOutput.flush();


		} catch (Exception e) {
			System.out.println("You couln't connect to the server");
			return;
		}
		PacketSender();
		packetReceiver();


	}

	void PacketSender()
	{

		packetSenderThread = new Thread(new Runnable()
		{

			public void run()
			{
				while(myRun)
				{
					if(  myGui != null && !myGui.getQueue().isEmpty())
					{
						DrawingTools tool = myGui.getQueue().poll();

						try {
							
							myOutput.writeObject(tool);
							myOutput.flush();


						} catch (IOException e) {

							e.printStackTrace();
						}
					}
					Thread.yield();
				}	
			}	
		});
		packetSenderThread.start();

	}

	void packetReceiver()
	{
		packetReceiverThread = new Thread(new Runnable()
		{

			public void run()
			{
				try {
					myInput = new ObjectInputStream(mySocket.getInputStream());
		
					while(myRun)
					{
						if(mySocket.isConnected())
						{
							DrawingTools drawing = (DrawingTools) myInput.readObject();
							myGui.addDrawing(drawing);
						}

						Thread.yield();
					}	
				} catch (IOException | ClassNotFoundException e) {

					try {
						mySocket.close();
					} catch (IOException e1) {
					
						e1.printStackTrace();
					}
					System.out.println("You have disconnected");
					packetSenderThread.stop();
					packetReceiverThread.stop();
				}
			}	
		});
		packetReceiverThread.start();

	}

}
