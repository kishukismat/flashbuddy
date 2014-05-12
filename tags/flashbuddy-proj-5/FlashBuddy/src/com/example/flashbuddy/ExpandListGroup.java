/**
 * ExpandListGroup is the abstract child class that permits parent classes
 * to interact with ExpandableListElement group elements
 * 
 * @author John Leidel
 * @author Chase Baker
 * @author Zack Falgout
 * @version 1.0
 * 
 */



package com.example.flashbuddy;

import java.util.ArrayList;

public class ExpandListGroup {
	
	private String Name;
	private ArrayList<ExpandListChild> Items;
	
	/**
	 * Returns the name associated with the target group element
	 * 
	 * @return the name of the target group element
	 * @see String
	 */
	public String getName() {
		return Name;
	}
	
	/**
	 * Sets the name of the target group list element
	 * 
	 * @param Name	the name of the target group
	 * @return void
	 * @see String
	 */
	public void setName( String Name ){
		this.Name = Name;
	}
	
	/**
	 * Returns a list of the associated child elements of the target group
	 * 
	 * @return Items a list of ExpandListChild child items
	 * @See ArrayList
	 */
	public ArrayList<ExpandListChild> getItems() {
		return Items;
	}
	
	/**
	 * Sets the item list for the respective target group
	 * 
	 * @param Items A list of child items for the target group
	 * @return void
	 */
	public void setItems( ArrayList<ExpandListChild> Items){
		this.Items = Items;
	}
}
