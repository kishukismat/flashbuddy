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

import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class FlashBuddyExecStudyDeck extends Activity {

	private String FileName;
	private FlashBuddyDeck myDeck;
	private List<FlashBuddyCard> cards;
	private FlashBuddyCard currentCard;
	private int currentCardIndex;
	private int numCards;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_buddy_exec_study_deck, menu);
		return true;
	}
	
	/**
	 * Forces the answer to be displayed in the answer box
	 * @param view
	 */
	public void onClickShowAnswer( View view ){
		TextView answerText = (TextView) findViewById(R.id.answerText);
		answerText.setText(this.currentCard.getAnswer());
	}
	
	/**
	 * Moves to the next card when the NEXT button is clicked
	 * @param view
	 */
	public void onClickNextCard( View view ){
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
		Intent returnUserIntent = new Intent( this, FlashBuddyUserActivity.class );
    	startActivity(returnUserIntent);
	}

}
