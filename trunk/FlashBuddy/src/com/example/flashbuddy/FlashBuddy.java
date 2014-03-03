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
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.flashbuddy.FlashBuddyAuth;

public class FlashBuddy extends Activity {

	
	private static final String TAG="FlashBuddyMainActivity";
	
	public final static String USERNAME_MESSAGE = "com.example.flashbuddy.USERNAME";
	public final static String AUTH_MESSAGE = "com.example.flashbuddy.AUTHENTICATION";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_buddy);
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
    	
    	Boolean success = Auth.Authenticate(username.getText().toString(), password.getText().toString());
    	if( success == true ){
    		Log.i(TAG, "Authentication success");
    		intent.putExtra( USERNAME_MESSAGE, username.getText().toString() );
    		startActivity( intent );
    		
    	}else{
    		Log.i(TAG, "Authentication failure");
    	}
    	
    }
    
    /**
     * Handles the button click from the Create User Account button
     * @param view the current application view
     */
    public void onClickCreateUser(View view) {
    	Intent failedIntent = new Intent( this, FlashBuddyDisplayedMessageActivity.class );
    	String failedMessage = "Authentication Failed!";
    	failedIntent.putExtra( AUTH_MESSAGE, failedMessage);
    	startActivity(failedIntent);
    }
}
