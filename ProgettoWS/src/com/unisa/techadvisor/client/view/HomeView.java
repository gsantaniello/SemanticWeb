package com.unisa.techadvisor.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface HomeView {
	
	public interface Presenter {
			
	}
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
}
