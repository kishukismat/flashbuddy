/**
 * FlashBuddy Deck Class
 * 
 * FlashBuddy Deck Class 
 * 
 * @author John Leidel
 * @author Zack Falgout
 * @author Chase Baker 
 * @version 1.0
 * 
 */

package com.example.flashbuddy;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.example.flashbuddy.XMLParse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;




public class FlashBuddyDeck {
	
	private static final String ns = null;
	private static final String TAG="FlashBuddyDeck:Class:";
	private String title;
	private String subject;
	private int numCards;
	//private FlashBuddyCard[] cards; /* static array ?? */
	private List<FlashBuddyCard> cards; /* lists.. must import java.util */

	
	/**
	 * FlashBuddyDeck Constructor
	 */
	public FlashBuddyDeck(){
		this.title = "";
		this.subject = "";
		this.numCards = 0;
		this.cards = null;
	}
	
	/**
	 * FlashBuddyDeck Constructor Overloaded to build new decks
	 * @param title title is a String representing a new title
	 * @param subject subject is a String representing a new subject
	 */
	public FlashBuddyDeck( String title, String subject ){
		this.title = title;
		this.subject = subject;
		this.numCards = 0;
		this.cards = null;
	}
	
	/**
	 * Returns a list of FlashBuddyDeck cards
	 * @return a list of cards
	 */
	public List<FlashBuddyCard> getCards(){
		return this.cards;
	}
	
	/**
	 * setTitle : sets the title of the target FlashBuddy deck
	 * @param title String variable for the title
	 * @return returns true on success, false otherwise
	 */
	public Boolean setTitle( String title ){
		
		if( title.length() == 0 ){
			return false;
		}
		
		this.title = title;
		return true;
	}
	
	/**
	 * getTitle : retrieves the title value from the target deck
	 * @return returns the title of the target deck
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * setSubject : sets the title of the target FlashBuddy deck
	 * @param subject String variable for the subject
	 * @return returns true on success, false otherwise
	 */
	public Boolean setSubject( String subject ){
		
		if( subject.length() == 0 ){
			return false;
		}
		
		this.subject = subject;
		return true;
	}

	/**
	 * getSubject : retrieves the subject value from the target deck
	 * @return returns the subject of the target deck
	 */
	public String getSubject() {
		return this.subject;
	}
	
	/**
	 * getNumCards : retrieves the number of cards in the current deck
	 * @return returns the number of cards in the current deck
	 */
	public int getNumCards(){
		return this.numCards;
	}
	
	/**
	 * readDeck : attempts to read a deck from the target filename 
	 * @param fileName is the fileName of the target xml file to read
	 * @return returns true on success, false otherwise
	 */
	public Boolean readDeck( InputStream in ) throws XmlPullParserException{
		
		XmlPullParser parser;
				
		Log.i(TAG,"Enter readDeck...");
		
		
		/*
		 * Open the file
		 */
				
		parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(in, null);
			
		try {
			parser.nextTag();
			this.readFeed(parser);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

			
			
		/*
		*this.cards = parser.parse(inputStream);
		*inputStream.close();
		*/
		
		
		Log.i(TAG,"Exiting readDeck...");
		
		return true;
	}
	
	/**
	 * readFeed walks the XML file and inserts the necessary data into the class object
	 * @param parser
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private void readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {

		if( this.cards == null){
			this.cards = new ArrayList<FlashBuddyCard>();
		}
		
		parser.require(XmlPullParser.START_TAG, ns, "deck");

		/**
		 * walk the XML tags
		 */
		while (parser.next() != XmlPullParser.END_TAG) {
			
			if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
			
			String name = parser.getName();
			if( name.equals("numcards")){
				/* found the number of cards */
				numCards = readNumCards(parser);
			}else if( name.equals("title")){
				/* found the title */
				title = readTitle(parser);
			}else if( name.equals("subject")){
				/* found the subject */
				subject = readSubject(parser);
			}else if( name.equals("card")){
				cards.add(readCard(parser));
			}else{
				skip(parser);
			}
		}
	}
	
	/**
	 * Parses a FlashBuddy Card Entry
	 * @param parser
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private FlashBuddyCard readCard(XmlPullParser parser) throws XmlPullParserException, IOException {
		
		parser.require(XmlPullParser.START_TAG, ns, "card");

		int cardId = -1;
		int cardTimer = 0;
		String cardName = null;
		String cardQuestion = null;
		String cardAnswer = null;
		
			
		while (parser.next() != XmlPullParser.END_TAG) {
			
			if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
			
			String name = parser.getName();
			if( name.equals("id")){
				/**
				 * Card ID field
				 */
				cardId = readCardId(parser);
			}else if( name.equals("timer")){
				/**
				 * Timer field
				 */
				cardTimer = readCardTimer(parser);
			}else if( name.equals("name")){
				/**
				 * Name field
				 */
				cardName = readCardName(parser);
			}else if( name.equals("question")){
				/**
				 * Question field
				 */
				cardQuestion = readCardQuestion(parser);
			}else if( name.equals("answer")){
				cardAnswer = readCardAnswer(parser);
			}else{
				skip(parser);
			}
		}

		/**
		 * return a new, fully formed card
		 */
		return new FlashBuddyCard(cardId,cardTimer,cardName,cardQuestion,cardAnswer);
	}

	/**
	 * Reads the ID field from a card entry
	 * @param parser
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private int readCardId(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "id");
		int id = Integer.parseInt(readText(parser));
		parser.require(XmlPullParser.END_TAG, ns, "id");
		return id;
	}
	
	/**
	 * Reads the card name from a card entry
	 * @param parser
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private String readCardName(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "name");
		String name = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "name");
		return name;
	}
	
	/**
	 * Reads the card question from a card entry
	 * @param parser
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private String readCardQuestion(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "question");
		String question = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "question");
		return question;
	}
	
	/**
	 * Reads the card answer from a card entry
	 * @param parser
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private String readCardAnswer(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "answer");
		String answer = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "answer");
		return answer;
	}
	
	/**
	 * Reads the timer field from a card entry
	 * @param parser
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private int readCardTimer(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "timer");
		int timer = Integer.parseInt(readText(parser));
		parser.require(XmlPullParser.END_TAG, ns, "timer");
		return timer;
	}
	
	/**
	 * Reads the number of cards from the XML file
	 * @param parser
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private int readNumCards(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "numcards");
		int numCards = Integer.parseInt(readText(parser));
		parser.require(XmlPullParser.END_TAG, ns, "numcards");
		return numCards;
	}
	
	/**
	 * Reads the title from the XML file
	 * @param parser
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "title");
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "title");
		return title;
	}
	
	/**
	 * Reads the subject from the XML file
	 * @param parser
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private String readSubject(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "subject");
		String subject = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "subject");
		return subject;
	}
	
	/**
	 * reads the text from the parser
	 * @param parser
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}

	/**
	 * Skips an unused entry
	 * @param parser
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
	
	/**
	 * writeDeck : attempts to write a deck out to a file
	 * @return returns true on success, false otherwise
	 */
	public Boolean writeDeck(FileOutputStream of) throws FileNotFoundException{
		
		FileOutputStream outputStream = of;
		
		/**
		 * Write all the values 
		 */
		/* 
		 * XML Header
		 */
		try {
			outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/* 
		 * <deck>
		 */
		try {
			outputStream.write("<deck>\n".getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * <numcards></numcards>
		 */
		String numCardsText = "<numcards>" + Integer.toString(this.numCards) + "</numcards>\n";
		try {
			outputStream.write(numCardsText.getBytes());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		/*
		 * <title></title>
		 */
		String titleText = "<title>" + this.title + "</title>\n";
		try {
			outputStream.write(titleText.getBytes());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		/*
		 * <subject></subject>
		 */
		String subjectText = "<subject>" + this.subject + "</subject>\n";
		try {
			outputStream.write(subjectText.getBytes());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		/* 
		 * Write the individual cards
		 */
		for( FlashBuddyCard card : this.cards ){
			writeCard( card, outputStream);
		}
		
		/* 
		 * </deck>
		 */
		try {
			outputStream.write("</deck>\n".getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/**
		 * Close the file
		 */
		//try {
		//	outputStream.close();
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
		return true;
	}
	
	/**
	 * writeCard : writes the target card data to the open outputStream 
	 * @param card
	 * @param outputStream
	 */
	private void writeCard( FlashBuddyCard card, FileOutputStream outputStream ){
		
		/*
		 * <card>
		 */
		try {
			outputStream.write("<card>\n".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * <id></id>
		 */
		String cardId = "<card>" + Integer.toString(card.getId()) + "</card>\n";
		try {
			outputStream.write(cardId.getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * <name></name>
		 */
		String cardName = "<name>" + card.getName() + "</name>\n";
		try {
			outputStream.write(cardName.getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		/*
		 * <timer></timer>
		 */
		String cardTimer = "<timer>" + Integer.toString(card.getTimer()) + "</timer>\n";
		try {
			outputStream.write(cardTimer.getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * <question></question>
		 */
		String cardQuestion = "<question>" + card.getQuestion() + "</question>\n";
		try {
			outputStream.write(cardQuestion.getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/*
		 * <answer></answer>
		 */
		String cardAnswer = "<answer>" + card.getAnswer() + "</answer>\n";
		try {
			outputStream.write(cardAnswer.getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/*
		 * </card>
		 */
		try {
			outputStream.write("</card>\n".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * addCard : creates a new card in the current deck
	 * @param id id is an integer for the new id
	 * @param timer timer is the new card's timer value in seconds
	 * @param name name is the name of the new card
	 * @param question question is the question for the new card
	 * @param answer answer is the answer for the new card
	 * @return returns true on success, false otherwise
	 */
	public Boolean addCard( int id,
						int timer, 
						String name, 
						String question, 
						String answer ){
		
		/*
		 * setup the array list 
		 */
		if( this.cards == null ){
			this.cards = new ArrayList<FlashBuddyCard>();
		}
		
		FlashBuddyCard card = new FlashBuddyCard (id, timer, name, question, answer);
		cards.add(card);
		this.numCards++;
		
		return true;
	}
	
	/**
	 * deleteCard : deletes a card at the target ID
	 * @param id id is an integer representing the card to delete
	 * @return returns true on successful deletion, false otherwise
	 */
	public Boolean deleteCard( int id, List<FlashBuddyDeck> theDeck, FlashBuddyCard theCard ){
		
		/* TODO : REMOVE THE TARGET CARD AT 'id' */
		
		/* step 1 : walk the deck and look for 'id' */
		/* step 2 : remove the card */
		
		int theId = theCard.getId();
		
		while(theDeck.iterator().hasNext())
		{
			if(theDeck.iterator().next().equals(theId))
				
				theDeck.remove(theDeck.indexOf(theCard));
		}
		
		this.numCards--;
		
		return true;
	}
}
