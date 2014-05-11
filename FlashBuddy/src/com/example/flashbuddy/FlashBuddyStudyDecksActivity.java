/**
 * FlashBuddy Study Decks Activity
 * 
 * FlashBuddy Study Decks Activity Class 
 * 
 * @author John Leidel
 * @author Zack Falgout
 * @author Chase Baker 
 * @version 1.0
 * 
 */

package com.example.flashbuddy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.example.flashbuddy.Adapter.ExpandListAdapter;
import com.example.flashbuddy.*;

import android.os.Bundle;
import android.app.Activity;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

public class FlashBuddyStudyDecksActivity extends Activity {

	private ExpandListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;
	private ExpandableListView ExpandList;
	
	public final static String FILE_MESSAGE = "com.example.flashbuddy.FILE";
	public final static String GROUP_MESSAGE = "com.example.flashbuddy.GROUP";
	
	private int selectedFile;
	private String FileName;
	private int Group;
	
	private static final String TAG="FlashBuddyStudyDecksActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_buddy_study_decks);
		
		/*
		 * list the files in our built-in directory 
		 */
		String[] builtinFiles = null;
		try {
			builtinFiles = this.getAssets().list("decks");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* 
		 * list the files from the user directory
		 */
		String[] userFiles = null;
		
		userFiles = getFilesDir().list();
		
		
		/* 
		 * setup the menu
		 */
		ExpandList = (ExpandableListView)findViewById(R.id.StudyFileList);
		ExpListItems = SetStandardGroups(builtinFiles, userFiles);
		ExpAdapter = new ExpandListAdapter(FlashBuddyStudyDecksActivity.this,ExpListItems);
		ExpandList.setAdapter(ExpAdapter);
		ExpandList.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
		ExpandList.setOnChildClickListener(

				new ExpandableListView.OnChildClickListener() {
			
					
					@Override
					public boolean onChildClick(ExpandableListView parent, View v,
							int groupPosition, int childPosition, long id) {
						
						int index = parent.getFlatListPosition(
								ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
						parent.setItemChecked(index, true);
						
						/*
						 * Set the index and the name of the selected file
						 */
						selectedFile = index;
						Log.i(TAG,"Retrieving child filename...");
						Group = groupPosition;
						//String NoXMLFileName = ExpListItems.get(groupPosition).getItems().get(childPosition).getName();
						//String XMLFileName = NoXMLFileName + ".xml";
						//FileName = XMLFileName;
						FileName = ExpListItems.get(groupPosition).getItems().get(childPosition).getName();
						FileName = FileName.replaceAll(" ", "_");
						FileName = FileName + ".xml";
						
						
						Log.i(TAG,"Filename is: "+FileName);
						Log.i(TAG,"Group is:"+Integer.toString(Group));
						
						//ExpandList.collapseGroup(0);
						ExpandList.collapseGroup(groupPosition);
						
						Button studyButton = (Button)findViewById(R.id.StudyDeck);
						studyButton.setBackgroundColor(Color.parseColor("#4FA044"));
						
						TextView selectedDeck = (TextView) findViewById(R.id.currentSelection);
						String cleanName = FileName.replaceAll("_"," ");
						cleanName = cleanName.replaceAll(".xml","");
						selectedDeck.setText(cleanName);
						
						return true;
					}
				});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_buddy_study_decks, menu);
		return true;
	}
	
	
	/**
	 * SetStandardGroup : setup the standard list items
	 * @param files files is an array of file names
	 * @return returns the array list items
	 */
	public ArrayList<ExpandListGroup> SetStandardGroups( String[] builtinFiles, 
														 String[] userFiles ){
		ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
		ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>(); 
		ArrayList<ExpandListChild> list3 = new ArrayList<ExpandListChild>();
		ExpandListGroup gru1 = new ExpandListGroup();
		ExpandListGroup gru2 = new ExpandListGroup();
		
		/* Add the group parents */
		gru1.setName( "       Built-In Decks");
		gru2.setName( "       User Decks");
		
		/* 
		 * Add the children
		 */
		if( builtinFiles.length == 0 ){
			ExpandListChild ch1 = new ExpandListChild();
			ch1.setName("NO DECKS FOUND");
			ch1.setTag(null);
			ch1.setSelected(false);
			list2.add(ch1);
		}else{
			for( String s : builtinFiles ){
				ExpandListChild ch1 = new ExpandListChild();
				String noXMLString = s.replaceAll(".xml","");
				noXMLString = noXMLString.replaceAll("_", " ");
				ch1.setName(noXMLString);
				//ch1.setName(s);
				ch1.setTag(null);
				ch1.setSelected(false);
				list2.add(ch1);
			}
		}
		
		
		/* 
		 * Add the children
		 */
		if( userFiles.length == 0 ){
			ExpandListChild ch2 = new ExpandListChild();
			ch2.setName("NO DECKS FOUND");
			ch2.setTag(null);
			ch2.setSelected(false);
			list3.add(ch2);
		}else{
			for( String s : userFiles ){
				ExpandListChild ch2 = new ExpandListChild();
				String noXMLString = s.replaceAll(".xml","");
				noXMLString = noXMLString.replaceAll("_", " ");
				ch2.setName(noXMLString);
				ch2.setTag(null);
				ch2.setSelected(false);
				list3.add(ch2);
			}
		}
		
		/* 
		 * add the groups and their children to the parent 
		 */
		gru1.setItems( list2 );
		gru2.setItems( list3 );
		list.add(gru1);
		list.add(gru2);
		
		return list;
	}
	

    // Handles the Timer button click
    public void onClickTimer(View view){
    	Intent showTimerIntent = new Intent (this, FlashBuddyTimerActivity.class);
    	
    	Intent carryUsername = getIntent();
		String username = carryUsername.getStringExtra(FlashBuddy.USERNAME_MESSAGE);
		showTimerIntent.putExtra( FlashBuddy.USERNAME_MESSAGE, username );
		
    	startActivity(showTimerIntent);
    }
	
	/**
	 * onClickStudyDeck : starts the target deck in a study session
	 * 
	 * @param view
	 */
	public void onClickStudyDeck( View view){
		
		/*
		 * Ensure that the filename is valid before spawning new Activity
		 * 
		 */
		if( FileName == null ){
			Log.i(TAG,"Filename has not been selected, its NULL");
			return ;
		}else if( FileName.length() == 0){
			Log.i(TAG,"Filename has not been selected, length==0");
			return ;
		}else if( FileName == "NO DECKS FOUND"){
			Log.i(TAG,"Filename is NO DECKS FOUND; Not a real deck");
			return ;
		}else{
		
			Intent intent = new Intent( this, FlashBuddyExecStudyDeck.class);
			intent.putExtra( FILE_MESSAGE, FileName );
			intent.putExtra( GROUP_MESSAGE, Group );
		
			Intent carryUsername = getIntent();
			String username = carryUsername.getStringExtra(FlashBuddy.USERNAME_MESSAGE);
			intent.putExtra( FlashBuddy.USERNAME_MESSAGE, username );
		
			startActivity(intent);
		}
	}

}
