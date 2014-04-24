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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;

public class FlashBuddyCreateUserActivity extends Activity {
	public final static String AUTH_MESSAGE = "com.example.flashbuddy.AUTHENTICATION";
	public static String PREF_FILE_NAME = "default";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_buddy_create_user);
				
		TextView createUserBanner=(TextView)findViewById(R.id.CreateUserBanner);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/FantasticParty.ttf");
        createUserBanner.setTypeface(typeFace);
        
        Button createButton = (Button)findViewById(R.id.create_user_button);
        createButton.scheduleDrawable(createButton.getBackground(), checkInfo2, 1000);
	}
	
	final Runnable checkInfo2 = new Runnable()
    {
    	public void run()
    	{
	    	EditText username = (EditText)findViewById(R.id.create_username_text);
	    	EditText password = (EditText)findViewById(R.id.create_password_text_1);
	    	EditText password2 = (EditText)findViewById(R.id.create_password_text_2);
	    	
	    	String theUsername = username.getText().toString();
			String thePassword = password.getText().toString();
			String thePassword2 = password2.getText().toString();
	    	
			Button createButton = (Button)findViewById(R.id.create_user_button);
			
			if (theUsername.equals("")) 
			{ 
				createButton.setBackgroundColor(Color.parseColor("#7b93af"));
			}
			if (thePassword.equals("")) 
			{ 
				createButton.setBackgroundColor(Color.parseColor("#7b93af"));
			}
			if (thePassword2.equals("")) 
			{ 
				createButton.setBackgroundColor(Color.parseColor("#7b93af"));
			}
			else
			{
				createButton.setBackgroundColor(Color.parseColor("#4FA044"));
			}
			
			createButton.scheduleDrawable(createButton.getBackground(), checkInfo2, 1000);
    	}
    };
	
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

}