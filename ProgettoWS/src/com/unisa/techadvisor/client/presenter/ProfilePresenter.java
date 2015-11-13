package com.unisa.techadvisor.client.presenter;

import java.util.ArrayList;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.unisa.techadvisor.client.TechAdvisorServiceAsync;
import com.unisa.techadvisor.client.event.UpdateProfileEvent;
import com.unisa.techadvisor.client.view.ProfileView;
import com.unisa.techadvisor.client.view.ProfileViewImpl;

public class ProfilePresenter implements Presenter, ProfileView.Presenter {


	private final TechAdvisorServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final ProfileView display;
	private final HasWidgets container;

	public ProfilePresenter(TechAdvisorServiceAsync rpcService2,HandlerManager eventBus2, ProfileViewImpl profileView,HasWidgets container2) {
		// TODO Auto-generated constructor stub
		this.rpcService = rpcService2;
		this.eventBus = eventBus2;
		this.display = profileView;
		this.display.setPresenter(this);
		this.container = container2;
		
	}

	@Override
	public void go(final HasWidgets container) {

		rpcService.getLanguage(new AsyncCallback<ArrayList<String>>() {

			@Override
			public void onSuccess(ArrayList<String> result) {
				// TODO Auto-generated method stub
				Widget w;
				
				if (result ==null){
					w = display.designTable(new ArrayList<String>());
				}else {
					w = display.designTable(result);
				}
				HTMLPanel html = new HTMLPanel("<div id='profileContent' class='panelContent'></div>");
				html.add(w, "profileContent");
				container.add(html.asWidget());
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onDeleteButton(ArrayList<String> languages) {
		// TODO Auto-generated method stub
		rpcService.removeLanguage(languages, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new UpdateProfileEvent());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onAddButton(String lang_list) {
		// TODO Auto-generated method stub
		rpcService.insertLanguage(lang_list, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new UpdateProfileEvent());
			}
		});
		
	}

	@Override
	public void capture(String onFocusString) {
		// TODO Auto-generated method stub
		rpcService.getLanguages(onFocusString, new AsyncCallback<ArrayList<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<String> result) {
				// TODO Auto-generated method stub
				if (result!=null){
					HTMLPanel html = new HTMLPanel("<div id='langContent' class='panelContent'></div>");
					Widget w = display.showLanguages(result);
					html.add(w,"langContent");
					container.add(html.asWidget());
				}
				
				
			}
		});
		
	}

}
