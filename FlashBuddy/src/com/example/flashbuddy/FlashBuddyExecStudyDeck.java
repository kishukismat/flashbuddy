/**
 * FlashBuddy Exec Study Decks Activity
 * 
 * FlashBuddy Exec Study Decks Activity Class 
 * 
 * @author John Leidel
 * @author Zack Falgout
 * @author Chase Baker 
 * @version 1.0
 * 
 */

package com.example.flashbuddy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FlashBuddyExecStudyDeck extends Activity {

	private String FileName;
	private int Group;
	private FlashBuddyDeck myDeck;
	private List<FlashBuddyCard> cards;
	private FlashBuddyCard currentCard;
	private int currentCardIndex;
	private int numCards;
	TextView timerView;
	private Timer cardTimer;
	int timerTick;
	int isCancelled;
	int isTimed;
	private final Handler cardHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_buddy_exec_study_deck);
		
		/* 
		 * Create a new intent 
		 */
		Intent intent = getIntent();
		
		/* 
		 * Receive the message from the parent
		 */
		FileName = intent.getStringExtra(FlashBuddyStudyDecksActivity.FILE_MESSAGE);
		Group = intent.getIntExtra(FlashBuddyStudyDecksActivity.GROUP_MESSAGE, 0);
		
		/* 
		 * Validate the file then read it into a Decks object
		 */
		myDeck = new FlashBuddyDeck();
		InputStream in = null;
		
		in = this.openDeckFile(FileName, Group);
		
		try {
			myDeck.readDeck(in);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		/*
		 * At this point, we have all the data for the current
		 * flash card deck.  Setup the interface based upon
		 * what we read.
		 */
		/* 
		 * get the list of cards
		 */
		this.cards = myDeck.getCards();
		this.numCards = myDeck.getNumCards();
		
		/* 
		 * get the first card
		 */
		this.currentCard = this.cards.get(0);
		this.currentCardIndex = 0;
		
		/* 
		 * Update the card designator field so the user knows where they are 
		 */
		TextView cardDesignator = (TextView) findViewById(R.id.cardDesignator);
		cardDesignator.setText("Card 1 of " + Integer.toString(myDeck.getNumCards()));
		
		/* 
		 * Set the first question
		 */
		TextView questionText = (TextView) findViewById(R.id.questionText);
		questionText.setText(this.currentCard.getQuestion());
		
		/*
		 * Set the answer box to a question mark
		 */
		TextView answerText = (TextView) findViewById(R.id.answerText);
		answerText.setText("?");
		
		/* 
		 * check to see if we need a timer thread
		 */
		timerView = (TextView) findViewById(R.id.timerText);
		if( this.currentCard.getTimer() > 0 ){
			
			/* 
			 * create a new timer object and an associated handler
			 */
			this.isCancelled = 0;
			this.isTimed = 1;
			timerTick = this.currentCard.getTimer();
			cardTimer = new Timer();
			cardTimer.scheduleAtFixedRate( new TimerTask() {
				@Override
		         public void run() {UpdateGUI();}
			}, 1000, 1000);
			
		}else{ 
			/* 
			 * no timer found, set it to zero
			 */
			this.isCancelled = 1;
			this.isTimed = 0;
			timerView.setText("0");
		}

		int theTime = FlashBuddyTimerActivity.getTotalTimer();
		final TextView totalStudyTime = (TextView) findViewById(R.id.totalStudyTimer);
		if (theTime != 0)
		{
			new CountDownTimer(theTime*1000, 1000) {
	
				     public void onTick(long millisUntilFinished) {
				    	 
				    	 int seconds=(int) ((millisUntilFinished/1000)%60);
				    	 long minutes=((millisUntilFinished-seconds)/1000)/60;
				    	 
				    	 int doubleSeconds=Integer.parseInt(Integer.toString(seconds));  
				    	 java.text.DecimalFormat nft = new  
				    	 java.text.DecimalFormat("#00.###");  
				    	 nft.setDecimalSeparatorAlwaysShown(false);  
				    	 
				    	 totalStudyTime.setText("Time Remaining: " + minutes + ":" + nft.format(doubleSeconds));    	
				     }
		
				     public void onFinish() {
				         totalStudyTime.setText("");
				         timerIsUp();
				     }
			}.start();
		}
		
	}

	@SuppressLint("NewApi")
	public void timerIsUp()
	{
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
		
        dlgAlert.setMessage("Time is Up!");
        dlgAlert.setTitle("Study Session Complete");
        dlgAlert.setPositiveButton("Continue", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
        
        final Dialog thisLayout = new Dialog(this);
    	thisLayout.setContentView(R.layout.activity_flash_buddy_exec_study_deck);

        dlgAlert.setPositiveButton("Continue", new DialogInterface.OnClickListener() 
        {
                public void onClick(DialogInterface dialog, int which) 
                {
                	dialog.dismiss();
                }
        });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_buddy_exec_study_deck, menu);
		return true;
	}
	
	/**
	 * Attempts to open the target filename of type "group"
	 * @param FileName is the raw filename
	 * @param Group is the group of calling ListView
	 * @return
	 */
	private InputStream openDeckFile( String FileName, int Group ){
		
		if( Group == 0){
			
			InputStream in = null;
			try {
				in = getAssets().open("decks/"+FileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return in;
			
		}else{
			InputStream in = null;
			
			try {
				in = openFileInput(FileName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return in;
		}
		
	}
	
	/**
	 * UpdateGUI : Updates the internal textview timer object with the correct time
	 */
	private void UpdateGUI() {
		
		/* 
		 * decrement the timer
		 */
		this.timerTick--;
	    
		/* 
		 * check to see if we have run out of time
		 */
		if( this.timerTick == 0 ){
			/*
			 * we've run out of time, move to the next card
			 * TODO: post a message saying you've run out of time
			 */
			this.cardHandler.post(myForceNextButtonClick);
		}else{
			/*
			 * update the new time
			 */
			this.cardHandler.post(myUpdateNewTime);
		}
	}
	
	/**
	 * Runnable thread for forcing a "next" button click 
	 */
	final Runnable myForceNextButtonClick = new Runnable(){
		public void run(){
			/*
			 * forcible click of the "next" button
			 */
			Button button = (Button) findViewById(R.id.AnswerButton);
			button.performClick();
		}
	};
	
	/**
	 * Runnable thread for updating the timer object
	 */
	final Runnable myUpdateNewTime = new Runnable()
	{
		public void run()
		{
			timerView.setText(String.valueOf(timerTick));
			
			if( timerTick == 6 || timerTick == 5 || timerTick == 4 )
			{
				// Text color should be yellow
				timerView.setTextColor(Color.parseColor("#FF9900"));
				timerView.setBackgroundResource(0x7f020003);
			}
			else if( timerTick == 3 || timerTick == 2 || timerTick == 1 )
			{
				// Text color should be red
				timerView.setTextColor(Color.parseColor("#DD514C"));
				timerView.setBackgroundResource(0x7f020002);
			}
			else
			{
				// Text color should be green
				timerView.setTextColor(Color.parseColor("#4FA044"));
				timerView.setTypeface(null,Typeface.BOLD);
				timerView.setBackgroundResource(0x7f020001);
			}
		}
	};
	
	/**
	 * Forces the answer to be displayed in the answer box
	 * @param view
	 */
	public void onClickShowAnswer( View view ){
		TextView answerText = (TextView) findViewById(R.id.answerText);
		answerText.setText(this.currentCard.getAnswer());
		
		/*
		 * cancel the timer 
		 */
		if( (this.isTimed==1) && (this.isCancelled==0)){
			this.cardTimer.cancel();
		}
		this.isCancelled = 1;
		
		Button answerButton = (Button)findViewById(R.id.AnswerButton);
		answerButton.setBackgroundColor(Color.parseColor("#7b93af"));
		
		Button nextButton = (Button)findViewById(R.id.nextButton);
		nextButton.setBackgroundColor(Color.parseColor("#4FA044"));
	}
	
	/**
	 * Moves to the next card when the NEXT button is clicked
	 * @param view
	 */
	public void onClickNextCard( View view ){
		Button answerButton = (Button)findViewById(R.id.AnswerButton);
		answerButton.setBackgroundColor(Color.parseColor("#4FA044"));
		
		Button nextButton = (Button)findViewById(R.id.nextButton);
		nextButton.setBackgroundColor(Color.parseColor("#7b93af"));
		
		if( (this.currentCardIndex+1) > (this.numCards-1)){
			/* 
			 * we reached the end of the deck, recycle it
			 */
			this.currentCardIndex = 0;
		}else{
			this.currentCardIndex++;
		}
		
		/* 
		 * get the appropriate card
		 */
		this.currentCard = this.cards.get(this.currentCardIndex);
		
		/*
		 * decide whether to setup the timer
		 */
		if( this.currentCard.getTimer() > 0 ){
			
			/* 
			 * reset the timer object and an associated handler
			 */
			timerTick = this.currentCard.getTimer();
			
			/* 
			 * logic to decide whether we need to cancel the previous timer
			 */
			if( (this.isTimed==1) && (this.isCancelled==0)){
				cardTimer.cancel();
			}
			this.isTimed = 1;
			this.isCancelled = 0;
			
			UpdateGUI();
			cardTimer = new Timer();
			cardTimer.scheduleAtFixedRate( new TimerTask() {
				@Override
		         public void run() {UpdateGUI();}
			}, 1000, 1000);
			
		}else{ 
			/* 
			 * no timer found, set text to zero and cancel timer
			 */
			if( (this.isTimed==1) && (this.isCancelled==0)){
				cardTimer.cancel();
			}
			this.isTimed = 0;
			this.isCancelled = 0;
			timerView.setText("0");
		}
		
		/* 
		 * setup the banner 
		 */
		TextView cardDesignator = (TextView) findViewById(R.id.cardDesignator);
		cardDesignator.setText("Card " + Integer.toString(this.currentCardIndex+1)
							+ " of " + Integer.toString(this.numCards) );
		
		/* 
		 * setup the question 
		 */
		TextView questionText = (TextView) findViewById(R.id.questionText);
		questionText.setText(this.currentCard.getQuestion());
		
		/*
		 * Set the answer box to a question mark
		 */
		TextView answerText = (TextView) findViewById(R.id.answerText);
		answerText.setText("?");
	}
	
	/**
	 * Returns to the user menu
	 * @param view
	 */
	public void onClickDoneStudying( View view ){
		// Create a new intent 
		Intent carryUsername = getIntent();
		String username = carryUsername.getStringExtra(FlashBuddy.USERNAME_MESSAGE);
		
		Intent returnUserIntent = new Intent( this, FlashBuddyUserActivity.class );
		returnUserIntent.putExtra( FlashBuddy.USERNAME_MESSAGE, username );
    	startActivity(returnUserIntent);
	}

}
