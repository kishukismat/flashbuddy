/*
 * Flash Buddy Timer Activity
 * Flash Buddy Timer Activity Class
 * 
 * @author John Leidel
 * @author Zack Falgout
 * @author Chase Baker 
 * @version 1.0
 */

package com.example.flashbuddy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Timer;

import com.example.flashbuddy.*;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;



public class FlashBuddyTimerActivity extends Activity {
	
	TextView timerView;
	private Timer sessionTimer;
	private static int totalSec;
	int theMinutes;
	int theSeconds;
	int timerTick;
	int isCancelled;
	int isTimed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session_timer);
		
	}
	
	public static int getTotalTimer()
	{
		return totalSec;
	}
	
	//Set the attributes for the session timer.
	public void setTotalTimer(int totalSec){
		FlashBuddyTimerActivity.totalSec = totalSec;
	}
	
	//When the Save button is clicked the values of the text boxes should be saved as the timer.
	public void onClickSaveTimer(View view){
		
		//Minute value
		EditText timerTextView1 = (EditText) findViewById(R.id.timerEntry);
		//Second Value
		EditText timerTextView2 = (EditText) findViewById(R.id.timerEntry2);
		
		if (timerTextView1.getText().toString() == "")
		{
			timerTextView1.setText("0");
		}
		if (timerTextView2.getText().toString() == "")
		{
			timerTextView2.setText("0");
		}
		
		//Parse the two string values to ints.
		int localTimerMin = Integer.parseInt(timerTextView1.getText().toString());
		int localTimerSec = Integer.parseInt(timerTextView2.getText().toString());

		int theTime = (localTimerMin * 60) + localTimerSec;
		
		this.setTotalTimer(theTime);
		
		//Return to user activity screen.
		Intent returnUserIntent = new Intent( this, FlashBuddyStudyDecksActivity.class );
		
		Intent carryUsername = getIntent();
		String username = carryUsername.getStringExtra(FlashBuddy.USERNAME_MESSAGE);
		returnUserIntent.putExtra( FlashBuddy.USERNAME_MESSAGE, username );
		
    	startActivity(returnUserIntent);
		
	}

		
}
