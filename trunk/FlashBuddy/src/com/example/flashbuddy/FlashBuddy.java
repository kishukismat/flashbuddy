/**
 * FlashBuddy Main Activity
 * 
 * FlashBuddy Authentication Class 
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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.flashbuddy.FlashBuddyAuth;

public class FlashBuddy extends Activity {

	
	private static final String TAG="FlashBuddyMainActivity";

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
    	EditText username = (EditText)findViewById(R.id.username_text);
    	EditText password = (EditText)findViewById(R.id.password_text);
    	
    	/* 
    	 * Attempt to Authenticate
    	 */
    	FlashBuddyAuth Auth = new FlashBuddyAuth();
    	
    	Boolean success = Auth.Authenticate(username.getText().toString(), password.getText().toString());
    	if( success == true ){
    		Log.i(TAG, "Authentication success");
    	}else{
    		Log.i(TAG, "Authentication failure");
    	}
    	
    }
    
    /**
     * Handles the button click from the Create User Account button
     * @param view the current application view
     */
    public void onClickCreateUser(View view) {
    	
    }
}
