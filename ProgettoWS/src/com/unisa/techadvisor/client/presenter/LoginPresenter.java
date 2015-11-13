package com.unisa.techadvisor.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.HasWidgets;
import com.unisa.techadvisor.client.TechAdvisorServiceAsync;
import com.unisa.techadvisor.client.event.HomeEvent;
import com.unisa.techadvisor.client.view.LoginView;
import com.unisa.techadvisor.client.view.LoginViewImpl;
import com.unisa.techadvisor.shared.Constants;
import com.unisa.techadvisor.shared.User;

public class LoginPresenter implements Presenter, LoginView.Presenter {

	
	private final TechAdvisorServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final LoginView display;
	
	
	public LoginPresenter(TechAdvisorServiceAsync rpcService, HandlerManager eventBus, LoginViewImpl loginView) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = loginView;
		this.display.setPresenter(this);
	}


	@Override
	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
		container.clear();
	    container.add(display.asWidget());

	}


	@Override
	public void onLoginButtonClicked(String email,String pswd) {
		// TODO Auto-generated method stub
		User usr = new User(email, pswd);
		rpcService.logIn(usr, new AsyncCallback<String>() {
		
			@Override
			public void onSuccess(String result) {
				if (result.equals(Constants.LOGIN_COMPLETATA)){
					eventBus.fireEvent(new HomeEvent());
				}
				else if (result.equals(Constants.LOGIN_FALLITA)) {
					display.setErrMsgLogin();
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				try {
					throw caught;
				} catch (IncompatibleRemoteServiceException e) {
					// this client is not compatible with the server; cleanup and refresh the browser 
					Window.alert("IncompatibleRemoteServiceException "+e.getMessage());
				} catch (InvocationException e) {
					// the call didn't complete cleanly
					Window.alert("InvocationException "+e.getMessage());
				} catch (Throwable e) {
					// last resort -- a very unexpected exception
					Window.alert("Throwable "+e.getMessage());
				}
			}
		});
		
	}


	@Override
	public void onSignUpButtonClicked(User usr) {
		// TODO Auto-generated method stub
		// eventuale controllo dei campi di usr
		
		rpcService.signUp(usr, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				if (result.equals(Constants.REGISTRAZIONE_COMPLETATA)){
					eventBus.fireEvent(new HomeEvent());
				}
				else {
					display.setErrMsgSignUp(result);
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				try {
					throw caught;
				} catch (IncompatibleRemoteServiceException e) {
					// this client is not compatible with the server; cleanup and refresh the browser 
					Window.alert("IncompatibleRemoteServiceException "+e.getMessage());
				} catch (InvocationException e) {
					// the call didn't complete cleanly
					Window.alert("InvocationException "+e.getMessage());
				} catch (Throwable e) {
					// last resort -- a very unexpected exception
					Window.alert("Throwable "+e.getMessage());
				}
			}
		});
	}

}
