/**
 * FlashBuddy Main Activity
 * 
 * FlashBuddy Main Activity Class 
 * 
 * @author John Leidel
 * @author Zack Falgout
 * @author Chase Baker 
 * @version 1.0
 * 
 */

package com.example.flashbuddy;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.flashbuddy.FlashBuddyAuth;

@SuppressLint("NewApi")
public class FlashBuddy extends Activity {
	
	private static final String TAG="FlashBuddyMainActivity";
	
	public final static String USERNAME_MESSAGE = "com.example.flashbuddy.USERNAME";
	public final static String AUTH_MESSAGE = "com.example.flashbuddy.AUTHENTICATION";
	
	public static String PREFS = "MyPrefs";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	//Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_buddy);
        
        TextView welcome=(TextView)findViewById(R.id.LogInBanner);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/FantasticParty.ttf");
        welcome.setTypeface(typeFace);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.flash_buddy, menu);
        return true;
    }
    
    /**
     * Handles the button click from the Log In button
     * @param view the current application view
     */
    public void onClickAuthUser(View view) {
    	
    	Intent intent = new Intent( this, FlashBuddyUserActivity.class);
    	
    	EditText username = (EditText)findViewById(R.id.username_text);
    	EditText password = (EditText)findViewById(R.id.password_text);
    	
    	/* 
    	 * Attempt to Authenticate
    	 */
    	FlashBuddyAuth Auth = new FlashBuddyAuth();
    	
    	String enteredUsername = username.getText().toString();
		String enteredPassword = password.getText().toString();
    	
		FlashBuddy.PREFS = enteredUsername;
    	final String AdminPREFERENCES = "admin";
    	final String Username = "usernameKey"; 
    	final String Password = "passwordKey"; 
    	String truePassword;
    
    	// Sets up the admin's shared preferences
    	SharedPreferences adminprefs = getApplicationContext().getSharedPreferences(AdminPREFERENCES, Context.MODE_PRIVATE);
    	Editor adminEditor = adminprefs.edit();
    	adminEditor.putString(Username, "admin");
    	adminEditor.putString(Password, "password");
    	adminEditor.commit();
    	
    	// Begins the current user's shared preference checking
    	SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    	
    	Boolean success = Auth.Authenticate(enteredUsername, enteredPassword);
    	if( success == true ){
    		Log.i(TAG, "Authentication success");
    		
    		//if(sharedpreferences.contains(enteredUsername))
    		if(enteredUsername.equals(sharedpreferences.getString(Username,"")))
    		{
    			truePassword = sharedpreferences.getString(Password, "");
    			if (truePassword.equals(enteredPassword))
    			{
    				intent.putExtra( USERNAME_MESSAGE, username.getText().toString() );
    	    		startActivity( intent );
    			}
    			else
    			{
    				Log.i(TAG, "Authentication failure");
    	    		Intent failedIntent = new Intent( this, FlashBuddyDisplayedMessageActivity.class );
    	        	String failedMessage = "Authentication Failed!  Please return and try again.";
    	        	failedIntent.putExtra( AUTH_MESSAGE, failedMessage);
    	        	startActivity(failedIntent);
    			}
    		}	
    		else
			{
				Log.i(TAG, "Authentication failure");
	    		Intent failedIntent = new Intent( this, FlashBuddyDisplayedMessageActivity.class );
	        	String failedMessage = "User not found!  Please return and try again.";
	        	failedIntent.putExtra( AUTH_MESSAGE, failedMessage);
	        	startActivity(failedIntent);
			}
    	}else{
    		Log.i(TAG, "Authentication failure");
    		Intent failedIntent = new Intent( this, FlashBuddyDisplayedMessageActivity.class );
        	String failedMessage = "No characters found!  Please enter your login information and try again.";
        	failedIntent.putExtra( AUTH_MESSAGE, failedMessage);
        	startActivity(failedIntent);
    	}
    	
    }
    
    /**
     * Handles the button click from the Create User Account button
     * @param view the current application view
     */
    public void onClickCreateUser(View view) {
    	Intent createUserIntent = new Intent( this, FlashBuddyCreateUserActivity.class );
    	startActivity(createUserIntent);
    }
}