package com.unisa.techadvisor.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface Search3_1View {

	public interface Presenter{
		void onWebSiteClicked();
		void onWebAppClicked();
	}
	
	Widget asWidget();
	void setPresenter(Presenter presenter);
}
