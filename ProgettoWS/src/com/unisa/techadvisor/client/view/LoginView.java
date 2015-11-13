package com.unisa.techadvisor.client.view;

import com.google.gwt.user.client.ui.Widget;
import com.unisa.techadvisor.shared.User;

public interface LoginView {

	public interface Presenter {
		void onLoginButtonClicked(String usr, String pswd);
		void onSignUpButtonClicked(User usr);
	}
	
	void setPresenter(Presenter presenter);
	Widget asWidget();
	void setErrMsgLogin();
	void setErrMsgSignUp(String errType);
}
