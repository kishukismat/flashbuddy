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



public class FlashBuddyDeck {
	
	private String title;
	private String subject;
	private int numCards;
	//private FlashBuddyCard[] cards; /* static array ?? */
	//private List<FlashBuddyCard> cards; /* lists.. must import java.util */
	
	/**
	 * FlashBuddyDeck Constructor
	 */
	public FlashBuddyDeck(){
		this.title = "";
		this.subject = "";
		this.numCards = 0;
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
	public Boolean readDeck( String fileName ){
		return true;
	}
	
	/**
	 * writeDeck : attempts to write a deck out to a file
	 * @return returns true on success, false otherwise
	 */
	public Boolean writeDeck(){
		return true;
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
