/**
 * ExpandListChild is the abstract child class that permits parent classes
 * to interact with ExpandableListElement child elements
 * 
 * @author John Leidel
 * @author Chase Baker
 * @author Zack Falgout
 * @version 1.0
 * 
 */


package com.example.flashbuddy;

public class ExpandListChild {

	/* 
	 * private variables
	 */
	private String Name;
	private String Tag;
	private Boolean Selected;
	
	/**
	 * Returns the name associated with the target child element
	 * 
	 * @return the name of the target child element
	 * @see String
	 */
	public String getName() {
		return Name;
	}
	
	/**
	 * Sets the name of the target child list element
	 * 
	 * @param Name	the name of the target child
	 * @return void
	 * @see String
	 */
	public void setName( String Name ){
		this.Name = Name;
	}
	
	/**
	 * Returns the tag associated with the target child element
	 * 
	 * @return the tag of the target child element
	 * @see String
	 * 
	 */
	public String getTag() {
		return Tag;
	}
	
	/**
	 * Sets the tag associated with the target child element
	 * 
	 * @param Tag	the tag of the target child element
	 * @return void
	 * @see String
	 */
	public void setTag( String Tag ){
		this.Tag = Tag;
	}
	
	/**
	 * Returns a boolean value of whether the target child has been selected
	 * 
	 * @return true if the child has been selected, false otherwise
	 * @see Boolean
	 */
	public Boolean getSelected(){
		return Selected;
	}
	
	/**
	 * Sets the selected attributed associated with the target child element
	 * 
	 * @param Select the boolean select value, true or false
	 * @return void
	 * @see Boolean
	 */
	public void setSelected( Boolean Select ){
		this.Selected = Select;
	}
}
