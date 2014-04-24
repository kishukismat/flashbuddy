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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class FlashBuddyExecStudyDeck extends Activity {

	private String FileName;
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
		
		/* 
		 * Validate the file then read it into a Decks object
		 */
		myDeck = new FlashBuddyDeck();
		try {
			InputStream in = null;
			try {
				in = getAssets().open("decks/"+FileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			myDeck.readDeck(in);
			
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (XmlPullParserException e) {
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
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_buddy_exec_study_deck, menu);
		return true;
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
			Button button = (Button) findViewById(R.id.nextButton);
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
				timerView.setTextColor(Color.parseColor("#CCFF66"));
			}
			else if( timerTick == 3 || timerTick == 2 || timerTick == 1 )
			{
				// Text color should be red
				timerView.setTextColor(Color.parseColor("#DD514C"));
			}
			else
			{
				// Text color should be green
				timerView.setTextColor(Color.parseColor("#4FA044"));
				timerView.setTypeface(null,Typeface.BOLD);
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
		Intent intent = getIntent();
		String username = intent.getStringExtra(FlashBuddy.USERNAME_MESSAGE);
		
		Intent returnUserIntent = new Intent( this, FlashBuddyUserActivity.class );
		returnUserIntent.putExtra( FlashBuddy.USERNAME_MESSAGE, username );
    	startActivity(returnUserIntent);
	}

}
