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

import android.util.Log;


public class FlashBuddyAuth {

	@SuppressWarnings("unused")
	private String username;
	@SuppressWarnings("unused")
	private String password;
	
	private static String TAG="FlashBuddy::FlashBuddyAuth";
	
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
