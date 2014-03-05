/**
 * FlashBuddy XML Parser Class
 * 
 * FlashBuddy XML Parser Class 
 * 
 * @author John Leidel
 * @author Zack Falgout
 * @author Chase Baker 
 * @version 1.0
 * 
 */

package com.example.flashbuddy;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
 
import com.example.flashbuddy.FlashBuddyCard;
 
public class XMLParse {
    //Create a list of cards.
	List<FlashBuddyCard> cards;
    private FlashBuddyCard card;
    private String text;
 
    //Constructor
    public XMLParse() {
        cards = new ArrayList<FlashBuddyCard>();
    }
 
    //Return the cards in the list.
    public List<FlashBuddyCard> getCards() {
        return cards;
    }
 
    //Parse the input stream.
    public List<FlashBuddyCard> parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        //Try Catch block
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
 
            parser.setInput(is, null);
 
            //Keep track of the event type to facilitate parsing of data.
            int eventType = parser.getEventType();
            //Loop until the end of the file.
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //Name of the tag.
            	String tagname = parser.getName();
                //Switch based on tags.
            	switch (eventType) {
                
            	//Create a card if that's the start tag.
            	case XmlPullParser.START_TAG:
                    if (tagname.equalsIgnoreCase("card")) {
                        //Create a new instance of card
                       FlashBuddyCard card = new FlashBuddyCard();
                    }
                    break;
                
                    //Get text between tags.
                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;
                
                //Add text from between the tags to the correct field of the card.
                case XmlPullParser.END_TAG:
                    if (tagname.equalsIgnoreCase("card")) {
                        //Add card object to list
                        cards.add(card);
                    }
                    //Add the text to the name field.
                    else if (tagname.equalsIgnoreCase("name")) {
                        card.setName(text);
                    } 
                    //Add id to the id field.
                    else if (tagname.equalsIgnoreCase("id")) {
                        card.setId(Integer.parseInt(text));
                    } 
                    //Add timer to the timer field.
                    else if (tagname.equalsIgnoreCase("timer")) {
                        card.setTimer(Integer.parseInt(text));
                    } 
                    //Add question to the question field.
                    else if (tagname.equalsIgnoreCase("question")) {
                        card.setQuestion(text);
                    } 
                    //Add answer to the answer field.
                    else if (tagname.equalsIgnoreCase("answer")) {
                        card.setAnswer(text);
                    }
                    break;
                
                //Break statement if none are true.
                default:
                    break;
                }
                //Update the next eventType.
            	eventType = parser.next();
            }
            
            //Catch the two exceptions.
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
          
        //Return the array of cards.
        return cards;
    }
}