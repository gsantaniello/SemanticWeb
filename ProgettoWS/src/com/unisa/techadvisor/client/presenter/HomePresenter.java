package com.unisa.techadvisor.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.unisa.techadvisor.client.TechAdvisorServiceAsync;
import com.unisa.techadvisor.client.view.HomeView;

public class HomePresenter implements Presenter, HomeView.Presenter {

	private final TechAdvisorServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final HomeView homeView;



	public HomePresenter(TechAdvisorServiceAsync rpcService, HandlerManager eventBus, HomeView homeView) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.homeView = homeView;
		this.homeView.setPresenter(this);
	}


	@Override
	public void go(final HasWidgets container) {
		// TODO Auto-generated method stub
		container.add(homeView.asWidget());
	}

}
