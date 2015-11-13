package com.unisa.techadvisor.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.unisa.techadvisor.client.TechAdvisorServiceAsync;
import com.unisa.techadvisor.client.event.ResultEvent;
import com.unisa.techadvisor.client.event.Search3_1Event;
import com.unisa.techadvisor.client.event.Search3_2Event;
import com.unisa.techadvisor.client.view.DivResult;
import com.unisa.techadvisor.client.view.Search2View;
import com.unisa.techadvisor.client.view.Search2ViewImpl;
import com.unisa.techadvisor.shared.Constants;
import com.unisa.techadvisor.shared.GenericKnowledgeClass;
import com.unisa.techadvisor.shared.ResultSearch;

public class Search2Presenter implements Presenter, Search2View.Presenter{

	/**
	 * classe da assegnare come stile al div che contiene il risultato parziale
	 */
	private static final String DIV_PARTIAL_RIS_CONTENT = "div_partialRisContent";
	private final TechAdvisorServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Search2View searchView;
	
	public Search2Presenter(TechAdvisorServiceAsync rpcService, HandlerManager eventBus, Search2ViewImpl search2View) {
		// TODO Auto-generated constructor stub
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.searchView= search2View;
		searchView.setPresenter(this);
		
	}

	@Override
	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
		container.add(searchView.asWidget());
		designPartialResult(container);
	}

	/**
	 * Costruisce la tabella con i risultati parziali
	 * @param container
	 */
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
	public void web() {
		// TODO Auto-generated method stub
		rpcService.setCategory("web",new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				if (result.equals(Constants.NO_MATCH)){
					eventBus.fireEvent(new ResultEvent());
				}else{
					eventBus.fireEvent(new Search3_1Event());
				}
			}
		});
		
	}

	@Override
	public void mobile() {
		// TODO Auto-generated method stub
		rpcService.setCategory("mobile",new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				if (result.equals(Constants.NO_MATCH)){
					eventBus.fireEvent(new ResultEvent());
				}else{
					eventBus.fireEvent(new Search3_2Event());
				}
				
			}
		});
	}

}
