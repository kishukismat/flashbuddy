/**
 * FlashBuddy Edit Deck Activity
 * 
 * FlashBuddy Edit Deck Activity Class 
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
import java.io.InputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FlashBuddyEditDeckActivity extends Activity {

	private String FileName;
	private List<FlashBuddyCard> cards;
	private FlashBuddyCard currentCard;
	private FlashBuddyDeck editDeck = null;
	private int currentCardIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_buddy_edit_deck);
		
		/* 
         * Grab the Intent
         */
        Intent intent = getIntent();
        FileName = intent.getStringExtra( FlashBuddyModifyDecksActivity.FILE_MESSAGE );
        
        /* 
         * read the deck into our editDeck object 
         */
        editDeck = new FlashBuddyDeck();
        try {
			InputStream in = null;
			try {
				in = openFileInput(FileName);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			 * read the deck into the object
			 */
			editDeck.readDeck(in);
			
			try {
				if( in != null ){
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        /* 
         * get the card data 
         */
        cards = editDeck.getCards();
        currentCard = cards.get(0);
        currentCardIndex = 1;
        
        /*
         * Update the text fields with the current data
         */
        TextView questionTextView = (TextView) findViewById(R.id.questionEntry);
		TextView answerTextView = (TextView) findViewById(R.id.answerEntry);
		TextView timerTextView = (TextView) findViewById(R.id.timerEntry);
		TextView cardDesignator = (TextView) findViewById(R.id.cardDesignator);
		
		questionTextView.setText(currentCard.getQuestion());
		answerTextView.setText(currentCard.getAnswer());
		timerTextView.setText(Integer.toString(currentCard.getTimer()));
		cardDesignator.setText("Card 1 of "+Integer.toString(editDeck.getNumCards()));
		
		Button NextButton = (Button)findViewById(R.id.nextButton);
		NextButton.scheduleDrawable(NextButton.getBackground(), checkInfo, 1000);
        
	}

	final Runnable checkInfo = new Runnable()
    {
    	public void run()
    	{
    		TextView questionTextView = (TextView) findViewById(R.id.questionEntry);
    		TextView answerTextView = (TextView) findViewById(R.id.answerEntry);
    		TextView timerTextView = (TextView) findViewById(R.id.timerEntry);
	    	
    		String localQuestion = questionTextView.getText().toString();
    		String localAnswer = answerTextView.getText().toString();
    		String localTimer = timerTextView.getText().toString();
	    	
    		Button NextButton = (Button)findViewById(R.id.nextButton);
			
			if (localQuestion.equals("") || localAnswer.equals("") || localTimer.equals("")) 
			{ 
				NextButton.setBackgroundColor(Color.parseColor("#7b93af"));
			}
			else
			{
				NextButton.setBackgroundColor(Color.parseColor("#4FA044"));
			}
			
			NextButton.scheduleDrawable(NextButton.getBackground(), checkInfo, 1000);
			}
    };
    
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_buddy_edit_deck, menu);
		return true;
	}

	/**
	 * onClickNextCard : flips the editing session to the next card
	 * @param view
	 */
	public void onClickNextCard( View view ){
	
		TextView questionTextView = (TextView) findViewById(R.id.questionEntry);
		TextView answerTextView = (TextView) findViewById(R.id.answerEntry);
		TextView timerTextView = (TextView) findViewById(R.id.timerEntry);
		TextView cardDesignator = (TextView) findViewById(R.id.cardDesignator);

		/* 
		 * grab the input values
		 */
		String localQuestion = questionTextView.getText().toString();
		String localAnswer = answerTextView.getText().toString();
		int localTimer = Integer.parseInt(timerTextView.getText().toString());
		
		/* 
		 * record them to the card object
		 */
		this.currentCard.setAnswer(localAnswer);
		this.currentCard.setQuestion(localQuestion);
		this.currentCard.setTimer(localTimer);
		
		if( (this.currentCardIndex+1) > this.editDeck.getNumCards() ) {
			
			/* 
			 * cycle back to the first card
			 */
			
			this.currentCardIndex = 1;
			this.cards = this.editDeck.getCards();
			this.currentCard = this.cards.get(0);
			
			/* 
			 * set the data in the text views
			 */
			answerTextView.setText(this.currentCard.getAnswer());
			questionTextView.setText(this.currentCard.getQuestion());
			timerTextView.setText(Integer.toString(this.currentCard.getTimer()));
			
		}else{
			
			/* 
			 * bump the data to the next card
			 */
			this.currentCardIndex++;
			this.cards = this.editDeck.getCards();
			this.currentCard = this.cards.get(this.currentCardIndex-1);
			
			/* 
			 * set the data in the text views
			 */
			answerTextView.setText(this.currentCard.getAnswer());
			questionTextView.setText(this.currentCard.getQuestion());
			timerTextView.setText(Integer.toString(this.currentCard.getTimer()));
		}
		
		cardDesignator.setText("Card " + Integer.toString(this.currentCardIndex) 
				+ " of " + Integer.toString(this.editDeck.getNumCards()));
	}
	
	/**
	 * onClickWriteCard : writes the card to disk
	 * @param view
	 */
	public void onClickWriteCard( View view ){
		
		if( this.FileName.length() == 0){
			/**
			 * DO NOTHING!  The user hasn't entered anything
			 */
			return ;
		}
		
		TextView questionTextView = (TextView) findViewById(R.id.questionEntry);
		TextView answerTextView = (TextView) findViewById(R.id.answerEntry);
		TextView timerTextView = (TextView) findViewById(R.id.timerEntry);

		/* 
		 * grab the input values
		 */
		String localQuestion = questionTextView.getText().toString();
		String localAnswer = answerTextView.getText().toString();
		int localTimer = Integer.parseInt(timerTextView.getText().toString());
		
		/* 
		 * write the data into the object
		 */
		this.currentCard = this.cards.get(this.currentCardIndex-1);
		this.currentCard.setAnswer(localAnswer);
		this.currentCard.setQuestion(localQuestion);
		this.currentCard.setTimer(localTimer);
		
		/*
		 * deletes the target file name
		 */
		deleteFile(this.FileName);
		
		/* 
		 * write out the file
		 */
		try {
			
			FileOutputStream of = openFileOutput(this.editDeck.getTitle() + ".xml", Context.MODE_PRIVATE);
			
			this.editDeck.writeDeck(of);
			
			/*
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
		
		/*
		 * done writing changes; return to ModifyDeckActivity
		 */
		Intent returnUserIntent = new Intent( this, FlashBuddyModifyDecksActivity.class );
		
		Intent intent = getIntent();
    	String username = intent.getStringExtra(FlashBuddy.USERNAME_MESSAGE);
    	returnUserIntent.putExtra( FlashBuddy.USERNAME_MESSAGE, username );
		
    	startActivity(returnUserIntent);
	}
	
}
