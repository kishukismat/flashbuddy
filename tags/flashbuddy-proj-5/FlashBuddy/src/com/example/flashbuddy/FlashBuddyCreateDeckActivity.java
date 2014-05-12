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
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FlashBuddyCreateDeckActivity extends Activity {

	public final static String DECK_NAME_MESSAGE = "com.example.flashbuddy.DECKNAME";
	public final static String DECK_SUBJECT_MESSAGE = "com.example.flashbuddy.DECKSUBJECT";
	public final static String DECK_NUMCARDS_MESSAGE = "com.example.flashbuddy.DECKNUMCARDS";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_buddy_create_deck);
		
		TextView CreateDeckBanner=(TextView)findViewById(R.id.CreateDeckBanner);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/FantasticParty.ttf");
        CreateDeckBanner.setTypeface(typeFace);
        
        Button createButton = (Button)findViewById(R.id.create_deck_button);
        createButton.scheduleDrawable(createButton.getBackground(), checkInfo, 1000);
	}
	
	final Runnable checkInfo = new Runnable()
    {
    	public void run()
    	{
    		EditText name = (EditText)findViewById(R.id.create_deck_name);
    		EditText subject = (EditText)findViewById(R.id.create_deck_subject);
    		EditText numcards = (EditText)findViewById(R.id.create_deck_numcards);
	    	
    		String Name = name.getText().toString();
    		String Subject = subject.getText().toString();
    		String NumCards = numcards.getText().toString();
	    	
    		Button createButton = (Button)findViewById(R.id.create_deck_button);
			
			if (Name.equals("") || Subject.equals("") || NumCards.equals("")) 
			{ 
				createButton.setBackgroundColor(Color.parseColor("#7b93af"));
			}
			else
			{
				createButton.setBackgroundColor(Color.parseColor("#4FA044"));
			}
			
			createButton.scheduleDrawable(createButton.getBackground(), checkInfo, 1000);
			}
    };

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
		
		Intent carryUsername = getIntent();
		String username = carryUsername.getStringExtra(FlashBuddy.USERNAME_MESSAGE);
		/* 
		 * Open the window for the card inputs
		 */
		Intent intent = new Intent( this, FlashBuddyCardInputActivity.class);
		intent.putExtra( DECK_NAME_MESSAGE, name.getText().toString() );
		intent.putExtra( DECK_SUBJECT_MESSAGE, subject.getText().toString() );
		intent.putExtra( DECK_NUMCARDS_MESSAGE, numcards.getText().toString() );
		intent.putExtra( FlashBuddy.USERNAME_MESSAGE, username );
		startActivity(intent);
						
		//Intent returnUserIntent = new Intent( this, FlashBuddyUserActivity.class );
    	//startActivity(returnUserIntent);
	}

}
