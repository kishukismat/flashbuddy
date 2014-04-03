/**
 * FlashBuddyAuth Class
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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.EditText;


public class FlashBuddyAuth {

	@SuppressWarnings("unused")
	private String username;
	@SuppressWarnings("unused")
	private String password;
	
	private static String TAG="FlashBuddy::FlashBuddyAuth";
	
	/**
	 * FlashBuddyAuth Constructor
	 */
	public FlashBuddyAuth(){
		this.username="";
		this.password="";
	}
	
	/**
	 * Authenticate: public method that authenticates a user to FlashBuddy
	 * @param username
	 * @param password
	 * @return returns true on a successful authentication, false otherwise
	 */
	public Boolean Authenticate( String username, String password ){
		
		/* 
		 * sanity check the inputs 
		 * 
		 */
		if( username.length() == 0 ){
			Log.i( TAG, "Username was of length 0");
			return false;
		}else if( password.length() == 0 ){ 
			Log.i( TAG, "Password was of length 0");
			return false;
		}
		
		/* 
		 * save the values locally
		 */
		this.username = username;
		this.password = password;
		
		/*
		 * TODO : INSERT AUTHENTICATION HERE 
		 */
		
		/* 
		 * clear the local values
		 */
		this.username = "";
		this.password = "";
		
		return true;
	}
	
}
