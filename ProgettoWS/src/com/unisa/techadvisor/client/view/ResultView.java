package com.unisa.techadvisor.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface ResultView{

	public interface Presenter {
		//metodi per modificare il profilo (almeno penso)
	}
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
	void setMsg();
	void deleteSetMsg();
}
