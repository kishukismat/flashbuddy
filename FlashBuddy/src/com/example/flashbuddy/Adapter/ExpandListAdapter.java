/**
 * ExpandListAdapters is the abstract class that provides the android
 * adapter functions to a parent class for initializing and interacting
 * with an ExpandableList GUI device
 * 
 * @author John Leidel
 * @author Chase Baker
 * @author Zack Falgout
 * @version 1.0
 * 
 */

package com.example.flashbuddy.Adapter;

import java.util.ArrayList;

import com.example.flashbuddy.R;
import com.example.flashbuddy.ExpandListChild;
import com.example.flashbuddy.ExpandListGroup;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandListAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	private ArrayList<ExpandListGroup> groups;
	
	/**
	 * Initializes the ListAdapter [Constructor]
	 *  
	 */
	public ExpandListAdapter(Context context, ArrayList<ExpandListGroup> groups) {
		this.context = context;
		this.groups = groups;
	}
	
	/**
	 * Adds a group and child item to the ExpandableList
	 * 
	 * @param item the target child item to add
	 * @param groups the target group to add or expand
	 * @return void
	 * 
	 */
	public void addItem(ExpandListChild item, ExpandListGroup group) {
		if (!groups.contains(group)) {
			groups.add(group);
		}
		
		int index = groups.indexOf(group);
		ArrayList<ExpandListChild> ch = groups.get(index).getItems();
		ch.add(item);
		groups.get(index).setItems(ch);
		
	}
	
	/**
	 * Returns the child object based upon the respective child position within the target group
	 * 
	 * @param int the target group
	 * @param int the target child position to retrieve
	 * @return Object
	 * 
	 */
	public Object getChild(int groupPosition, int childPosition) {
		
		ArrayList<ExpandListChild> chList = groups.get(groupPosition).getItems();
		return chList.get(childPosition);

	}
	
	/**
	 * Returns the child id of the target group and child position
	 * 
	 * @param int the target group
	 * @param int the target child position to retrieve
	 * @return long
	 * 
	 */
	public long getChildId( int groupPosition, int childPosition ){
		return childPosition;
	}
	
	/**
	 * Returns the View id of the target group and child position
	 * 
	 * @param int the target group
	 * @param int the target child position to retrieve
	 * @param boolean indicates whether the target child is the last in the list
	 * @param View the parent view
	 * @param ViewGroup the parent group view
	 * @return View
	 * 
	 */
	public View getChildView(int groupPosition, int childPosition, 
						boolean isLastChild, View view,ViewGroup parent) {
		ExpandListChild child = (ExpandListChild)getChild( groupPosition, childPosition);
		
		if( view == null ){
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.expandlist_child_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.tvChild);
		
		tv.setText(child.getName().toString());
		tv.setTag(child.getTag());

		return view;
	}
	
	/**
	 * Returns the number of children in the target group
	 * 
	 * @param int the target group
	 * @return int 
	 * 
	 */
	public int getChildrenCount(int groupPosition) {
		ArrayList<ExpandListChild> chList = groups.get(groupPosition).getItems();
		return chList.size();
	}
	
	/**
	 * Returns the group object of the target group position
	 * 
	 * @param int the target group position
	 * @return Object
	 * 
	 */
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}
	
	/**
	 * Returns the number of groups 
	 * 
	 * @return int
	 * 
	 */
	public int getGroupCount() {
		return groups.size();
	}
	
	/**
	 * Returns the group ID of the target group position
	 * 
	 * @param int the target group position
	 * @return long
	 * 
	 */
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	/**
	 * Returns the View object of the group view
	 * 
	 * @param int the target group position
	 * @param boolean determines whether the group is the last child
	 * @param View the apparent target view
	 * @param ViewGroup the target ViewGroup
	 * @return Object
	 * 
	 */
	public View getGroupView(int groupPosition, boolean isLastChild, 
					View view, ViewGroup parent) {
		ExpandListGroup group = (ExpandListGroup) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.expandlist_group_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.tvGroup);
		tv.setText(group.getName());

		return view;
	}

	/**
	 * Returns whether the List has been initialized
	 * 
	 * @return boolean
	 * 
	 */
	public boolean hasStableIds() {
		return true;
	}
	
	/**
	 * Returns whether the child is selectable
	 * 
	 * @param int arg0
	 * @param int arg1 
	 * @return boolean
	 * 
	 */
	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}
}
