package controller;

import java.util.ArrayList;

public class BuyList {

	private ArrayList<ListItem> items = new ArrayList<ListItem>();
	
	public void addItem(ListItem i){
		items.add(i);
	}

	public void removeItem(ListItem i){
		items.remove(i);
	}

	
	
	
}
