package com.unisa.techadvisor.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface Search2View {
	
	public interface Presenter{
		void web();
		void mobile();
	}
	
	Widget asWidget();
	void setPresenter(Presenter presenter);

}
