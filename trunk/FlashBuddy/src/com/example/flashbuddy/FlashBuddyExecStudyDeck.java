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

import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class FlashBuddyExecStudyDeck extends Activity {

	private String FileName;
	private FlashBuddyDeck myDeck;
	
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_buddy_exec_study_deck, menu);
		return true;
	}

}
