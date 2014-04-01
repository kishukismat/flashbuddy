/**
 * FlashBuddy Modify Decks Activity
 * 
 * FlashBuddy Modify Decks Activity Class 
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class FlashBuddyModifyDecksActivity extends Activity {

	
	private ExpandListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;
	private ExpandableListView ExpandList;
	
	private int selectedFile = -1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_buddy_modify_decks);
		
		/*
		 * list the files in our directory 
		 */
		String[] files = null;
		try {
			files = this.getAssets().list("decks");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* 
		 * setup the menu
		 */
		ExpandList = (ExpandableListView)findViewById(R.id.ModifyFileList);
		ExpListItems = SetStandardGroups(files);
		ExpAdapter = new ExpandListAdapter(FlashBuddyModifyDecksActivity.this,ExpListItems);
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
						
						selectedFile = index;
						
						ExpandList.collapseGroup(0);
						
						return true;
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flash_buddy_modify_decks, menu);
		return true;
	}
	
	/**
	 * SetStandardGroup : setup the standard list items
	 * @param files files is an array of file names
	 * @return returns the array list items
	 */
	public ArrayList<ExpandListGroup> SetStandardGroups( String[] files){
		ArrayList<ExpandListGroup> list = new ArrayList<ExpandListGroup>();
		ArrayList<ExpandListChild> list2 = new ArrayList<ExpandListChild>(); 
		
		/* Add the Group */
		ExpandListGroup gru1 = new ExpandListGroup();
		gru1.setName( "Decks");
		
		/* 
		 * Add the children
		 */
		if( files.length == 0 ){
			ExpandListChild ch1 = new ExpandListChild();
			ch1.setName("NO DECKS FOUND");
			ch1.setTag(null);
			ch1.setSelected(false);
			list2.add(ch1);
		}else{
			for( String s : files ){
				ExpandListChild ch1 = new ExpandListChild();
				ch1.setName(s);
				ch1.setTag(null);
				ch1.setSelected(false);
				list2.add(ch1);
			}
		}
		
		gru1.setItems( list2 );
		list.add(gru1);
		return list;
	}
	
	public void onClickNewDeck( View view ){
		Intent createDecksIntent = new Intent( this, FlashBuddyCreateDeckActivity.class );
		startActivity( createDecksIntent );
	}

}
