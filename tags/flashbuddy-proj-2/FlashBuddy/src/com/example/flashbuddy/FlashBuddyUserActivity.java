/**
 * FlashBuddy User Activity
 * 
 * FlashBuddy User Activity Class 
 * 
 * @author John Leidel
 * @author Zack Falgout
 * @author Chase Baker 
 * @version 1.0
 * 
 */

package com.example.flashbuddy;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class FlashBuddyUserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_buddy_user);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Create a new intent 
		Intent intent = getIntent();	

		// Receive the message from the parent
		String message = intent.getStringExtra(FlashBuddy.USERNAME_MESSAGE);
		 
		// Attach the textview to an existing one in the layout
		TextView textView = (TextView) findViewById(R.id.usernameTextview);
		
		//textView.setTextSize( 32 );
		textView.setText( message );
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_buddy_user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    /**
     * Handles the Logout button click
     */
    public void onClickLogout(View view) {
    	Intent logoutIntent = new Intent( this, FlashBuddy.class );
    	startActivity(logoutIntent);
    }
    
    /**
     * Handles the Decks button click
     * @param view view is the current view in the application
     */
    public void onClickShowDecks( View view ){
    	Intent showDecksIntent = new Intent( this, FlashBuddyModifyDecksActivity.class );
    	startActivity( showDecksIntent );
    	
    }

}
