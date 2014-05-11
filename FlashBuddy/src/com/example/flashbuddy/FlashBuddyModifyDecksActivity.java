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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.example.flashbuddy.Adapter.ExpandListAdapter;
import com.example.flashbuddy.*;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

public class FlashBuddyModifyDecksActivity extends Activity {

	
	private ExpandListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;
	private ExpandableListView ExpandList;
	private String FileName;
	private int selectedFile;
	public final static String FILE_MESSAGE = "com.example.flashbuddy.FILE";
	
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
		
		files = getFilesDir().list();
				
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
						
						/*
						 * Set the index and the name of the selected file
						 */
						selectedFile = index;
						
						FileName = ExpListItems.get(groupPosition).getItems().get(childPosition).getName();
						FileName = FileName + ".xml";
																		
						ExpandList.collapseGroup(0);
						
						Button editButton = (Button)findViewById(R.id.EditDeck);
						editButton.setBackgroundColor(Color.parseColor("#4FA044"));
						
						Button shareButton = (Button)findViewById(R.id.ShareDeck);
						shareButton.setBackgroundColor(Color.parseColor("#4FA044"));
						
						Button deleteButton = (Button)findViewById(R.id.DeleteDeck);
						deleteButton.setBackgroundColor(Color.parseColor("#dd514c"));
						
						TextView selectedDeck = (TextView) findViewById(R.id.currentSelection2);
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
		gru1.setName( "       Decks");
		
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
				String noXMLString = s.replaceAll(".xml","");
				noXMLString = noXMLString.replaceAll("_", " ");
				ch1.setName(noXMLString);
				//ch1.setName(s);
				ch1.setTag(null);
				ch1.setSelected(false);
				list2.add(ch1);
			}
		}
		
		gru1.setItems( list2 );
		list.add(gru1);
		return list;
	}
	
	/**
	 * onClickShareDeck : handles the button click for the "Share Deck" button
	 * @param view
	 */
	public void onClickShareDeck( View view ){

		/* 
		 * make sure the file name is valid
		 */
		if( this.FileName.length() == 0 ){
			return ;
		}
		
		/* 
		 * Step 1 : copy the file to an external location
		 */
		InputStream in = null;
		OutputStream out = null;
		File outFile = null;
		
		try{ 
			in = openFileInput(FileName);
			outFile = new File(getExternalFilesDir(null), FileName);
			outFile.createNewFile();
			
			out = new FileOutputStream(outFile);
			
			copyFile(in, out);
			in.close();
	        in = null;
	        out.flush();
	        out.close();
	        out = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* 
		 * Step 2 : attach and send an email 
		 */
		Intent email=new Intent(android.content.Intent.ACTION_SEND);
		//String pathname= Environment.getExternalStorageDirectory().getAbsolutePath();
		File myFile=new File(FileName);
		email.setType("plain/text");
		//email.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(getExternalFilesDir(null),FileName)));
		email.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(myFile));
		email.putExtra(Intent.EXTRA_SUBJECT, "FlashBuddy Study Deck : " + FileName);
		email.putExtra(Intent.EXTRA_TEXT, "Sharing FlashBuddy Study Deck " + FileName + " with you!");
		startActivity(Intent.createChooser(email, "E-mail"));
		
		/*
		 * Step 3 : delete the temporary file from external storage
		 */
		outFile.delete();

	}
	
	/**
	 * copyFile : copys from the input buffer to the output buffer
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	private void copyFile(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int read;
	    while((read = in.read(buffer)) != -1){
	      out.write(buffer, 0, read);
	    }
	}
	
	/**
	 * onClickNewDeck : handles the button click for the "Create Deck" button
	 * @param view
	 */
	public void onClickNewDeck( View view ){
		Intent carryUsername = getIntent();
		String username = carryUsername.getStringExtra(FlashBuddy.USERNAME_MESSAGE);
		
		Intent createDecksIntent = new Intent( this, FlashBuddyCreateDeckActivity.class );
		createDecksIntent.putExtra( FlashBuddy.USERNAME_MESSAGE, username );
		startActivity( createDecksIntent );
	}
	
	/**
	 * onClickEditDeck : handles the button click for the "Edit Deck" button 
	 * @param view
	 */
	public void onClickEditDeck( View view ){
		Intent carryUsername = getIntent();
		String username = carryUsername.getStringExtra(FlashBuddy.USERNAME_MESSAGE);
		
		Intent intent = new Intent( this, FlashBuddyEditDeckActivity.class);
		intent.putExtra( FILE_MESSAGE, FileName );
		intent.putExtra( FlashBuddy.USERNAME_MESSAGE, username );
		startActivity(intent);
	}
	
	/**
	 * onClickDeleteDeck : handles the button click for the "Delete Deck" button
	 * @param view
	 */
	public void onClickDeleteDeck( View view ){
		
		if( this.FileName.length() == 0){
			/**
			 * DO NOTHING!  The user hasn't entered anything
			 */
			return ;
		}
		
		/**
		 * deletes the target file name
		 * TODO: insert a dialog box to confirm the deletion
		 */
		
		deleteFile(this.FileName);
		
		/** 
		 * reset the drop-down box
		 */
		String[] files = getFilesDir().list();
		this.ExpListItems = SetStandardGroups(files);
		this.ExpAdapter = new ExpandListAdapter(FlashBuddyModifyDecksActivity.this,ExpListItems);
		this.ExpandList.setAdapter(this.ExpAdapter);
	}

}
