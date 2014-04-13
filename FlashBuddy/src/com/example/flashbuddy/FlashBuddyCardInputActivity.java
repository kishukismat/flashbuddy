/**
 * FlashBuddy Card Input Activity
 * 
 * FlashBuddy Card Input Activity Class 
 * 
 * @author John Leidel
 * @author Zack Falgout
 * @author Chase Baker 
 * @version 1.0
 * 
 */

package com.example.flashbuddy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class FlashBuddyCardInputActivity extends Activity {

	private String deckName;
	private String deckSubject;
	private int deckNumCards;
	private int currentCardIndex;
	private int recycle;
	private List<FlashBuddyCard> cards;
	private FlashBuddyCard currentCard;
	private FlashBuddyDeck myDeck = null;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_buddy_card_input);
		
		//TextView CreateDeckBanner=(TextView)findViewById(R.id.CreateDeckBanner);
        //Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/FantasticParty.ttf");
        //CreateDeckBanner.setTypeface(typeFace);
        
        /* 
         * Grab the Intent
         */
        Intent intent = getIntent();
        
        deckName = intent.getStringExtra(FlashBuddyCreateDeckActivity.DECK_NAME_MESSAGE);
        deckSubject = intent.getStringExtra(FlashBuddyCreateDeckActivity.DECK_SUBJECT_MESSAGE);
        deckNumCards = Integer.parseInt(intent.getStringExtra(FlashBuddyCreateDeckActivity.DECK_NUMCARDS_MESSAGE));
        currentCardIndex = 1;
        recycle = 0;
        
        TextView cardDesignator = (TextView) findViewById(R.id.cardDesignator);
        cardDesignator.setText("Card 1 of " + Integer.toString(deckNumCards));
        
        TextView timerView = (TextView) findViewById(R.id.timerEntry);
        timerView.setText("0");
        
        myDeck = new FlashBuddyDeck(deckName,deckSubject);
        
        /* 
         * we have the skeleton of our new deck, iterate over deckNumCards
         * and insert new cards.  this is done using the onClickNextCard member function
         */
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_buddy_card_input, menu);
		return true;
	}
	
	/**
	 * onClickNextCard : bumps the card to the next item; handles when the data wraps around
	 * @param view
	 */
	public void onClickNextCard( View view ){
		
		TextView questionTextView = (TextView) findViewById(R.id.questionEntry);
		TextView answerTextView = (TextView) findViewById(R.id.answerEntry);
		TextView timerTextView = (TextView) findViewById(R.id.timerEntry);
		TextView cardDesignator = (TextView) findViewById(R.id.cardDesignator);
		
		String localQuestion = questionTextView.getText().toString();
		String localAnswer = answerTextView.getText().toString();
		int localTimer = Integer.parseInt(timerTextView.getText().toString());
		
		if( (this.currentCardIndex+1) > this.deckNumCards ) {
			/* 
			 * go back to the beginning
			 * 
			 */
			this.currentCardIndex = 1;
			this.recycle = 1;
			
			/* 
			 * grab the list of cards from the deck 
			 */
			this.cards = this.myDeck.getCards();
			this.currentCard = this.cards.get(0);
			
			answerTextView.setText(this.currentCard.getAnswer());
			questionTextView.setText(this.currentCard.getQuestion());
			timerTextView.setText(Integer.toString(this.currentCard.getTimer()));
			
		}else if( this.recycle == 1){

			/* 
			 * we have already cycled through the cards
			 * update the card at id = currentCardIndex-1
			 */
			
			this.currentCard.setAnswer(localAnswer);
			this.currentCard.setQuestion(localQuestion);
			this.currentCard.setTimer(localTimer);

			this.currentCardIndex++;
			this.currentCard = this.cards.get(this.currentCardIndex-1);
			
			/**
			 * set the text for the next card in the current view
			 */
			answerTextView.setText(this.currentCard.getAnswer());
			questionTextView.setText(this.currentCard.getQuestion());
			timerTextView.setText(Integer.toString(this.currentCard.getTimer()));
			
		}else {
			
			/*
			 * Create a new card in the deck
			 * 
			 */
			this.myDeck.addCard(currentCardIndex, localTimer, 
							"Question # "+Integer.toString(this.currentCardIndex), 
							localQuestion, localAnswer);
			
			this.currentCardIndex++;
			timerTextView.setText("0");
			answerTextView.setText(" ");
			questionTextView.setText(" ");
		}
		
		cardDesignator.setText("Card " + Integer.toString(this.currentCardIndex) 
									+ " of " + Integer.toString(deckNumCards));
	}
	
	/**
	 * onClickWriteCard : writes the card to internal storage
	 * @param view
	 */
	public void onClickWriteCard( View view ){
		
		TextView questionTextView = (TextView) findViewById(R.id.questionEntry);
		TextView answerTextView = (TextView) findViewById(R.id.answerEntry);
		TextView timerTextView = (TextView) findViewById(R.id.timerEntry);
		
		/* 
		 * grab the data from the text views
		 */
		String localQuestion = questionTextView.getText().toString();
		String localAnswer = answerTextView.getText().toString();
		int localTimer = Integer.parseInt(timerTextView.getText().toString());
		
		if( this.recycle != 1 ){
			/* 
			 * we need to write out the last card
			 */
			this.myDeck.addCard(currentCardIndex, localTimer, 
					"Question # "+Integer.toString(this.currentCardIndex), 
					localQuestion, localAnswer);
		}else{
			/*
			 * write the data out to a specific card
			 */
			this.currentCard = this.cards.get(this.currentCardIndex-1);
			this.currentCard.setAnswer(localAnswer);
			this.currentCard.setQuestion(localQuestion);
			this.currentCard.setTimer(localTimer);
		}
		
		try {
			/**
			 * open the file
			 */
			FileOutputStream of = openFileOutput(this.deckName + ".xml", Context.MODE_PRIVATE);
			
			/**
			 * write out the file
			 */
			this.myDeck.writeDeck(of);
			
			/**
			 * close the file
			 */
			try {
				of.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Intent returnUserIntent = new Intent( this, FlashBuddyUserActivity.class );
    	startActivity(returnUserIntent);
	}

}
