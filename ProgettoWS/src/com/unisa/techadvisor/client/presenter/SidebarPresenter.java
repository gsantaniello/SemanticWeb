package com.unisa.techadvisor.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.unisa.techadvisor.client.TechAdvisorServiceAsync;
import com.unisa.techadvisor.client.event.HomeEvent;
import com.unisa.techadvisor.client.event.LogoutEvent;
import com.unisa.techadvisor.client.event.ProfileEvent;
import com.unisa.techadvisor.client.event.SearchEvent;
import com.unisa.techadvisor.client.view.SidebarView;
import com.unisa.techadvisor.client.view.SidebarViewImpl;

public class SidebarPresenter implements Presenter, SidebarView.Presenter {

	@Override
	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
		container.clear();
	    container.add(display.asWidget());
	}

	private final TechAdvisorServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final SidebarView display;
	  
	
	public SidebarPresenter(TechAdvisorServiceAsync rpcService, HandlerManager eventBus, SidebarViewImpl display) {
	    this.rpcService = rpcService;
	    this.eventBus = eventBus;
	    this.display = display;
	    this.display.setPresenter(this);
	    setName();
	}


	@Override
	public void onClickProfile() {
		// TODO Auto-generated method stub
		eventBus.fireEvent(new ProfileEvent());

	}


	@Override
	public void onClickSearch() {
		// TODO Auto-generated method stub
		eventBus.fireEvent(new SearchEvent());
		
	}


	@Override
	public void onClickHome() {
		// TODO Auto-generated method stub
		eventBus.fireEvent(new HomeEvent());
	}


	@Override
	public void onClickLogout() {
		eventBus.fireEvent(new LogoutEvent());
	}
	
	public void setName(){
		rpcService.getName(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				if (result!= null) {
					display.setName(result);
				}
				
			}
		});
	}
	
}
