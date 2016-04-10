package com.example.wificar;
import java.net.*;
import java.io.*;

import android.util.Log;
public class SocketClass {
	private String serverName;
	private String serverIP="++++++++++";
	private static int port = 23;
	private Socket client;
	private OutputStream outToServer;
	private DataOutputStream out;
	private String inFromServer;
	private DataInputStream in;
	private String connectedOrNot="Not Connected";
	public SocketClass(String name, int portNumber, String ip)
	{
		serverName=name;
		port = portNumber;
		serverIP=ip;
		
		///////////////////////////////////////
		try{
			client = new Socket(serverIP,port);	
			connectedOrNot="Connected";
			Log.w("myApp", "Sockets Work!!! ");
		}
				
		catch(IOException e)
		{
			e.printStackTrace();
			Log.w("myApp", "error in network File: "+ e.getMessage());
			connectedOrNot="Connected ERROR";
		}
		//////////////////////////////////////
	}
	public boolean getConnectionStatus()
	{
		if(connectedOrNot.equals("Connected"))
			return true;
	
		return false;
	}
	public void sendMessage(String message)
	{
		//////////////////////////////////////
		try {
			outToServer = client.getOutputStream();
			out=new DataOutputStream(outToServer);
			out.write(message.getBytes("UTF-8"));
			Log.w("MyApp", "Socket succeded to send Message!!!");
		} catch (IOException e) {
			
			Log.w("MyApp", "Socket Failed to send Message");
		}
		///////////////////////////////////////
		
	}
	public String receiveMessage()
	{

		///////////////////////////////////////
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader (client.getInputStream()));
			inFromServer = inFromClient.readLine();

			return inFromServer;
		} catch (IOException e) {
			Log.w("ERROR", e.toString());
			return e.toString();
		}
	}
		////////////////////////////////////////
		
	public String toString()
	{
		
		return "Connected to Server: "+serverName +" with IP address of "+serverIP+ " through port "+port+".";
		
	}
		
		////////////////////////////////////////
		

	public String bannerGrab()
	{
		sendMessage("WhoAreYou");
		return receiveMessage();
	}
	public void closeConnection() throws IOException
	{
		client.close();
	}
}
