/**
 * Session Timer for FlashBuddy
 * 
 * Sess_Timer and FlashBuddyTimer Classes 
 * 
 * @author John Leidel
 * @author Zack Falgout
 * @author Chase Baker 
 * @version 1.0
 * 
 */

package com.example.flashbuddy;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Sess_Timer extends Activity implements OnClickListener {
 
	private CountDownTimer sessionTimer;
	private boolean running = false;
	private Button start;
	public TextView text;
	//startTime variable to be set by the user.
	private long startTime;
	//Set interval to one second.
	private long interval = 1 * 1000;
	//How many seconds are left for the session.
	long secLeft = startTime / 1000;
	//How many minutes are left for the session.
	long minLeft = secLeft / 60;
	
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			/*
			 * Create the button and layout for the timer.
			 */
			
			setContentView(R.layout.session_timer);
			start = (Button) this.findViewById(R.id.session_timer);
			start.setOnClickListener(this);
			text = (TextView) this.findViewById(R.id.session_timer);
			sessionTimer = new FlashBuddyTimer(startTime, interval);
			text.setText(minLeft + ":" + secLeft);
 }
 
	@Override
	public void onClick(View v) {
		
		/*
		 * If running is false then start the timer and set the
		 * start button to "Stop", giving the user a chance to
		 * pause.
		 */
		if (!running) {
			sessionTimer.start();
			running = true;
			start.setText("Stop");
		} else 
		/*
		 * If running is true then cancel the timer and give the
		 * user a chance to restart it.	
		 */
		{
			sessionTimer.cancel();
			running = false;
			start.setText("Restart");
  }
}

	/*
	 * Inner helper class, FlashBuddyTimer.
	 */
	public class FlashBuddyTimer extends CountDownTimer {
  
		
		public FlashBuddyTimer(long startTime, long interval) {
			super(startTime, interval);
		}
 
		@Override
		/*
		 * Sets the background of the timer window to red 
		 * once the timer hits 0.
		 */
		public void onFinish() {
			text.setBackgroundColor(Color.RED);
 	}
 
		@Override
		/*
		 * Changes the display every second to reflect the remaining time.
		 */
		public void onTick(long timeLeft) {
			text.setText(minLeft + ":" + secLeft);
	}
		
		/*
		 * Gets input from the user and sets the timer accordingly.
		 */
		public void getInput(){
			//TODO
		}
 }
 
}