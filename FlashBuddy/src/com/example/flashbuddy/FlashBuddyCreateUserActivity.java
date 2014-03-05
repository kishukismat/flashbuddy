/**
 * FlashBuddy CreateUser Activity
 * 
 * FlashBuddy Create User Activity Class 
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
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

public class FlashBuddyCreateUserActivity extends Activity {
	public final static String AUTH_MESSAGE = "com.example.flashbuddy.AUTHENTICATION";
	public static String PREF_FILE_NAME = "default";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_buddy_create_user);
		// Show the Up button in the action bar.
		setupActionBar();
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
		getMenuInflater().inflate(R.menu.flash_buddy_create_user, menu);
		return true;
	}

	/**
	 * onClickCreateUser creates a new FlashBuddy User Account
	 * @param view the current view pane
	 */
	public void onClickCreateUser(View view){
		EditText username = (EditText)findViewById(R.id.create_username_text);
    	EditText password = (EditText)findViewById(R.id.create_password_text_1);
    	EditText confirmPassword = (EditText)findViewById(R.id.create_password_text_2);
    	
    	String enteredUsername = username.getText().toString();
		String enteredPassword = password.getText().toString();
		String enteredPassword2 = confirmPassword.getText().toString();
    	final String Username = "usernameKey"; 
    	final String Password = "passwordKey"; 
		
    	
    	if(enteredPassword.equals(enteredPassword2))
    	{
    		// Sets up the new user's shared preferences
    		FlashBuddyCreateUserActivity.PREF_FILE_NAME = enteredUsername;
    		//PREF_FILE_NAME = enteredUsername;
        	SharedPreferences sharedPrefs = getApplicationContext().getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        	SharedPreferences.Editor editor = sharedPrefs.edit();
        	editor.putString(Username, enteredUsername);
        	editor.putString(Password, enteredPassword);
        	editor.commit();
        	
        	try
        	{ 
        		Intent leave = new Intent(FlashBuddyCreateUserActivity.this, FlashBuddy.class);
        		startActivity(leave);
        	}catch(Exception e){
        		
        	}

    	}
    	else
    	{		
    		Intent failedIntent = new Intent( this, FlashBuddyDisplayedMessageActivity.class );
    		SharedPreferences settings = getSharedPreferences("admin", 
    			    Context.MODE_PRIVATE);
    			String myString = settings.getString(Password, "");
        	String failedMessage = myString;
        	//String failedMessage = "Passwords do not match!  Please hit the back button and try again.";
        	failedIntent.putExtra( AUTH_MESSAGE, failedMessage);
        	startActivity(failedIntent);
    	}
		
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

}