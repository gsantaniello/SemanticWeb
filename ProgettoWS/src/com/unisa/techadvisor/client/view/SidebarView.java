package com.unisa.techadvisor.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface SidebarView {

	public interface Presenter {

		void onClickProfile();
		void onClickSearch();
		void onClickHome();
		void onClickLogout();
	}
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
	void setName(String name);

}
