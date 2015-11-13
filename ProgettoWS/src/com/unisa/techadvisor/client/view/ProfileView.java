package com.unisa.techadvisor.client.view;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

public interface ProfileView {
	
	public interface Presenter {
		//metodi per modificare il profilo (almeno penso)
		void onDeleteButton(ArrayList<String> languages);
		void onAddButton(String argument);
		void capture(String onFocusString);
	}
	
	void setPresenter(Presenter presenter);
	Widget designTable(ArrayList<String> languages);
	Widget asWidget();
	Widget showLanguages(ArrayList<String> lngs);
}
