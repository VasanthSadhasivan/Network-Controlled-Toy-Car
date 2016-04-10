package com.example.wificar;


import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	AlertDialog.Builder alert;
	boolean onclickFlag=true, dataDumpFlag=true, buttonThreeFlag = true, buttonTwoFlag = true;
	int number;
	Button inputButtonOne, inputButtonTwo, inputButtonThree, dumpButton, disconnectButton;
	EditText mEditOne, mEditTwo, portInput, IPInput;
	TextView display, dumpDataView;
	SocketClass socket;
	
	
	@SuppressLint("NewApi")
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		    Log.w("myApp","HURRAYY");
		}

		
		number=0;
		inputButtonOne = (Button) findViewById(R.id.buttonOne);
		inputButtonTwo = (Button) findViewById(R.id.buttonTwo);
		inputButtonThree = (Button) findViewById(R.id.buttonThree);
		dumpButton = (Button) findViewById(R.id.dumpButton);
		mEditOne   = (EditText)findViewById(R.id.editText2);
		mEditTwo   = (EditText)findViewById(R.id.editText3);
		portInput = (EditText) findViewById(R.id.editText1);
		IPInput = (EditText) findViewById(R.id.editText4);
		dumpDataView = (TextView)findViewById(R.id.dumpData);
		display = (TextView) findViewById(R.id.tvDisplay);
		disconnectButton = (Button) findViewById(R.id.disconnectButton);
	
////////////////////////////////////////////////////////////////////
		
		
		inputButtonOne.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				//when button is clicked.... the code below is executed
				if (onclickFlag)
				{
					socket = new SocketClass("Edison", Integer.parseInt(portInput.getText().toString()), IPInput.getText().toString());
					onclickFlag=!onclickFlag;
				}
				if (socket.getConnectionStatus()){
					
					
					display.setText("Connected !!");
					}

				else
					display.setText("Not Connected !!");				
			}
		});
		
/////////////////////////////////////////////////
		
		disconnectButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!socket.getConnectionStatus())
				{
					alert = new AlertDialog.Builder(MainActivity.this);
					alert.setTitle("Connection not create");
					alert.show();
				}
				else
				{
					try {
						socket.closeConnection();
						display.setText("Not Connected !!");	
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
/////////////////////////////////////////////////
		
		
		inputButtonTwo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(buttonTwoFlag){
					inputButtonTwo.setText("Low");
					socket.sendMessage("H INPUT 1: " + mEditOne.getText().toString());
				}
				else{
					inputButtonTwo.setText("High");
					socket.sendMessage("L INPUT 1: " + mEditOne.getText().toString());
				}
				buttonTwoFlag = !buttonTwoFlag;
				
			}
		});
		
/////////////////////////////////////////////////
	
		inputButtonThree.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(buttonThreeFlag){
					inputButtonThree.setText("Low");
					socket.sendMessage("H INPUT 2: " + mEditTwo.getText().toString());
				}
				else{
					inputButtonThree.setText("High");
					socket.sendMessage("L INPUT 2: " + mEditTwo.getText().toString());
				}
				buttonThreeFlag = !buttonThreeFlag;
				
				
			}
		});
		
////////////////////////////////////////////////////
		
		dumpButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			    final Handler handler = new Handler();
			    final Runnable runnable = new Runnable() {
			        public void run() {
			        	
			            int i=0;
			        	while(i<41){
			        		i++;
			        		try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			                handler.post(new Runnable(){
			                	
			                    public void run() {
			                    	
			    					String temp="";
			    					
									
									Log.w("DUMP","Trying to dump data");
									temp=socket.receiveMessage();
									Log.w("DUMP", temp);
									dumpDataView.append(temp+"\n");
			                    

			                }
			            });
			        	}
			            
			        }
			    };
			    dumpDataView.setText("");
			    new Thread(runnable).start();

				
			}
		});
		
/////////////////////////////////////////////////////

	}	
}
