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
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.TextView;


@SuppressLint("NewApi")
public class HelpLogin extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	//Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_login);
        
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
    
    @Override
    public boolean onTouchEvent(MotionEvent event){ 
            
        int action = MotionEventCompat.getActionMasked(event);
            
        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
            	Intent loginIntent = new Intent( this, FlashBuddy.class );
        		startActivity(loginIntent);
        		finish();
                return true;
            case (MotionEvent.ACTION_MOVE) :
                return true;
            case (MotionEvent.ACTION_UP) :
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                return true;      
            default : 
                return super.onTouchEvent(event);
        }      
    }
      
}