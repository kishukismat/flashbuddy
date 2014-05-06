/**
 * FlashBuddy Card Class
 * 
 * @author John Leidel
 * @author Zack Falgout
 * @author Chase Baker 
 * @version 1.0
 * 
 */

package com.example.flashbuddy;

public class FlashBuddyCard {

	private int id;
	private int timer;
	private String name;
	private String question;
	private String answer;
	
	/**
	 * FlashBuddyCard default constructor
	 */
	public FlashBuddyCard(){
		this.id = 0;
		this.timer = 0;
		this.name = "";
		this.question = "";
		this.answer = "";
	}
	
	
	/**
	 * FlashBuddyCard Overloaded Constructor
	 * @param id is a valid ID for the card
	 * @param timer timer is the timer for the student in seconds
	 * @param name name is a String name for the card
	 * @param question question is a String question for the front of the card
	 * @param answer answers is a String answer for the back of the card
	 */
	public FlashBuddyCard( int id,
					int timer, 
					String name, 
					String question, 
					String answer ){
		
		this.id = id;
		this.timer = timer;
		this.name = name;
		this.question = question;
		this.answer = answer;
	}
	
	/**
	 * getId : returns the ID of the target card
	 * @return returns the id of the target card
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * setId : sets the ID for the target card
	 * @param id id is an integer
	 * @return returns true on success, false otherwise
	 */
	public Boolean setId( int id ){
		this.id = id;
		
		return true;
	}
	
	/**
	 * getTimer : retrieves the timer from the target card
	 * @return returns an integer for the current card's timer
	 */
	public int getTimer(){
		return this.timer;
	}
	
	/**
	 * setTimer : sets the timer for the target card
	 * @param timer is an integer representing the timer in seconds
	 * @return returns true on success, false otherwise
	 */
	public Boolean setTimer( int timer ){
		this.timer = timer;
		
		return true;
	}
	
	/**
	 * getName : retrieves the name of the target card
	 * @return returns a String for the name of the current card
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * setName : sets the name for the target card
	 * @param name name is a String representing the name for the target card
	 * @return returns true on success, false otherwise
	 */
	public Boolean setName( String name ){
		this.name = name;
		
		return true;
	}
	
	/**
	 * getQuestion : returns the question from the target card
	 * @return returns a String with the question from the target card
	 */
	public String getQuestion(){
		return this.question;
	}
	
	/**
	 * setQuestion : sets the target card's question to the String question
	 * @param question question is a String representing the question phrase
	 * @return returns true on success, false otherwise
	 */
	public Boolean setQuestion( String question ){ 
		this.question = question;
		
		return true;
	}
	
	/**
	 * getAnswer : retrieves the answer for the target card
	 * @return returns a String with the answer for the target card
	 */
	public String getAnswer(){
		return this.answer;
	}
	
	/**
	 * setAnswer : sets the answer phrase for the target card
	 * @param answer answers is a String representing the answer phrase
	 * @return returns true on success, false otherwise 
	 */
	public Boolean setAnswer( String answer ){
		this.answer = answer;
		
		return true;
	}
}
