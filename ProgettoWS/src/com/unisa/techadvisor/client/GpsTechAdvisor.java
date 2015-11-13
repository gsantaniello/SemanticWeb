package com.unisa.techadvisor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GpsTechAdvisor implements EntryPoint {
	
	public void onModuleLoad() {
		final TechAdvisorServiceAsync rpcService = GWT.create(TechAdvisorService.class);
		rpcService.setOntology(new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				//Window.alert("qui ci sono ");
				HandlerManager eventBus = new HandlerManager(null);
				AppController appViewer = new AppController(rpcService, eventBus);
			    appViewer.go(RootPanel.get("contentPage"));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
