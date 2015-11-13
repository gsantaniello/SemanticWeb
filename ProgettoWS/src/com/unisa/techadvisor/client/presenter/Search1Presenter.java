package com.unisa.techadvisor.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.unisa.techadvisor.client.TechAdvisorServiceAsync;
import com.unisa.techadvisor.client.event.ResultEvent;
import com.unisa.techadvisor.client.event.Search2Event;
import com.unisa.techadvisor.client.view.Search1View;
import com.unisa.techadvisor.client.view.Search1ViewImpl;
import com.unisa.techadvisor.shared.Constants;

public class Search1Presenter implements Presenter, Search1View.Presenter  {

	
	private final TechAdvisorServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Search1View searchView;
	
	public Search1Presenter(TechAdvisorServiceAsync rpcService, HandlerManager eventBus, Search1ViewImpl searchView) {
		// TODO Auto-generated constructor stub
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.searchView = searchView;
		this.searchView.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
		container.add(searchView.asWidget());
		
	}

	@Override
	public void next(String argument) {
		// TODO Auto-generated method stub
		rpcService.setResult(argument, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				if (result.equals(Constants.SET_TOPIC)){
					eventBus.fireEvent(new Search2Event());
				}else if (result.equals(Constants.RESULT_SEARCH)){
					eventBus.fireEvent(new ResultEvent());
				}else if (result.equals(Constants.NO_MATCH)){
					searchView.setNoMatch();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

}
