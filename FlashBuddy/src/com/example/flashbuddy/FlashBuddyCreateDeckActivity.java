/**
 * FlashBuddy Create Deck Activity
 * 
 * FlashBuddy Create Deck Activity Class 
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

import com.example.flashbuddy.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class FlashBuddyCreateDeckActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_buddy_create_deck);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_buddy_create_deck, menu);
		return true;
	}
	
	public void onClickCreateDeck(View view){
	
		EditText name = (EditText)findViewById(R.id.create_deck_name);
		EditText subject = (EditText)findViewById(R.id.create_deck_subject);
		EditText numcards = (EditText)findViewById(R.id.create_deck_numcards);
		int numCardsInt = Integer.parseInt(numcards.getText().toString());
		
		/* 
		 * create the new deck
		 */
		FlashBuddyDeck deck = new FlashBuddyDeck( name.getText().toString(), 
										subject.getText().toString() );
		
		/* 
		 * for numcards, create the new cards
		 */
		/*
		 * TODO
		 */
		
		/* 
		 * write the deck out to a file 
		 */
		try {
			FileOutputStream of = openFileOutput( deck.getTitle()+".xml", Context.MODE_PRIVATE);
			deck.writeDeck(of);
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
