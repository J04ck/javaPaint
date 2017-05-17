package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import paint.DrawingTools;

public class Client{

	ObjectInputStream myIn;
	ObjectOutputStream myOut; 
	Socket mySocket;
	String myUserName;
	public boolean myError = false;

	Client(Socket aSocket)
	{
		
		mySocket = aSocket;
		try {
			myIn = new ObjectInputStream((mySocket.getInputStream()));
			myOut = new ObjectOutputStream(mySocket.getOutputStream());


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientCheck();
	}


	private void clientCheck()
	{
		try {
		
			long currentTime = System.currentTimeMillis() + 1000;
			while (currentTime > System.currentTimeMillis())
			{
				if (mySocket.getInputStream().available() > 1)
				{
				
					myUserName = (String) myIn.readObject();
				}
			}
			//System.out.println(myUserName);
			myError = false;
		} catch (IOException | ClassNotFoundException e) {

			e.printStackTrace();
			myError = true;
		}

	}
	
	void sendData(DrawingTools aTool)
	{
		try {
			myOut.writeObject(aTool);
			myOut.flush();
			System.out.println("toClientServerSide");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	
	DrawingTools getData()
	{
		try {
			if (mySocket.getInputStream().available() > 1)
			{
				
				return (DrawingTools) myIn.readObject();
				
			}
			else {
				return null;
			}
		} catch (IOException | ClassNotFoundException e) {
			
			e.printStackTrace();
		
		} 
		return null;
	}
	
	@Override
	public String toString()
	{
		return myUserName + "  " + mySocket.getInetAddress();
	}
	
	public void disconnect()
	{
		try {
			mySocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
