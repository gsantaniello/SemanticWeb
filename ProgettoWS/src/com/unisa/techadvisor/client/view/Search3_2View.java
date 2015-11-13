package com.unisa.techadvisor.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface Search3_2View {

	public interface Presenter{
		void onAndroidClicked();
		void onIOSClicked();
		void onWindowsPhoneClicked();
		
	}
	
	Widget asWidget();
	void setPresenter(Presenter presenter);
	
}
