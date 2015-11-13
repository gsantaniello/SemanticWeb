package com.unisa.techadvisor.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.unisa.techadvisor.client.TechAdvisorServiceAsync;
import com.unisa.techadvisor.client.event.ResultEvent;
import com.unisa.techadvisor.client.view.DivResult;
import com.unisa.techadvisor.client.view.Search3_1View;
import com.unisa.techadvisor.client.view.Search3_1ViewImpl;
import com.unisa.techadvisor.shared.GenericKnowledgeClass;
import com.unisa.techadvisor.shared.ResultSearch;

public class Search3_1Presenter implements Presenter, Search3_1View.Presenter {

	/**
	 * classe da assegnare come stile al div che contiene il risultato parziale
	 */
	private static final String DIV_PARTIAL_RIS_CONTENT = "div_partialRisContent";
	
	private final TechAdvisorServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Search3_1View searchView;
	
	public Search3_1Presenter(TechAdvisorServiceAsync rpcService,HandlerManager eventBus, Search3_1ViewImpl search3View) {
		// TODO Auto-generated constructor stub
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.searchView = search3View;
		searchView.setPresenter(this);
		
		
	}

	@Override
	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
		container.add(searchView.asWidget());
		designPartialResult(container);
	}

	private void designPartialResult(final HasWidgets container) {
		// TODO Auto-generated method stub
		rpcService.resultFromSessionServer(new AsyncCallback<ResultSearch>() {
			
			@Override
			public void onSuccess(ResultSearch result) {
				// TODO Auto-generated method stub
				HTMLPanel html = new HTMLPanel("<div id='partialResult' class='partialContentResult'><h1>Partial Result</h1></div>");
				for (GenericKnowledgeClass res: result.getResult()){
					DivResult unDiv = new DivResult(res,DIV_PARTIAL_RIS_CONTENT);
					html.add(unDiv, "partialResult");
				}
				container.add(html);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onWebSiteClicked() {
		// TODO Auto-generated method stub
		rpcService.setSubCategory("website", new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new ResultEvent());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onWebAppClicked() {
		// TODO Auto-generated method stub
		rpcService.setSubCategory("web application", new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				eventBus.fireEvent(new ResultEvent());
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	
	

}
