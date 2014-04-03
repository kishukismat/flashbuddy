package com.example.flashbuddy;

import java.io.IOException;
import com.example.flashbuddy.XMLParse;
import android.os.Bundle;
import java.util.List;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
 
public class XMLParseActivity extends Activity {
 
    ListView listView;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_buddy);
 
        listView = (ListView) findViewById(0);
 
        //Create a list of cards.
        List<FlashBuddyCard> cards = null;
        //Attempt to parse the XML from the local asset folder.
        try {
            XMLParse parser = new XMLParse();
            cards = parser.parse(getAssets().open("Grade_One_Addition.xml"));
            ArrayAdapter<FlashBuddyCard> adapter =
                new ArrayAdapter<FlashBuddyCard>(this,R.layout.activity_flash_buddy, cards);
            listView.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.flash_buddy, menu);
        return true;
    }
 
}

