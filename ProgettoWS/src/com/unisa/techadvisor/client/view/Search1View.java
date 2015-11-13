package com.unisa.techadvisor.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface Search1View {

	
	public interface Presenter{
		void next(String argument);
	}
	
	Widget asWidget();
	void setPresenter(Presenter presenter);
	void setNoMatch();
}
