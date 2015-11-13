package com.unisa.techadvisor.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.unisa.techadvisor.client.TechAdvisorServiceAsync;
import com.unisa.techadvisor.client.view.DivResult;
import com.unisa.techadvisor.client.view.ResultView;
import com.unisa.techadvisor.shared.GenericKnowledgeClass;
import com.unisa.techadvisor.shared.ResultSearch;

/**
 * Presenter che gestisce la logica della pagina finale
 * @author giuseppe
 */
public class ResultPresenter implements Presenter, ResultView.Presenter {
	
	/**
	 * classe da dare come stile al div nella pagina finale
	 */
	private static final String DIV_FINAL_RIS_CONTENT = "div_finalRisContent";
	private final TechAdvisorServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final ResultView resultView;
	
	
	public ResultPresenter(TechAdvisorServiceAsync rpcService, HandlerManager eventBus, ResultView resultView) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.resultView = resultView;
		this.resultView.setPresenter(this);
		
	}

	@Override
	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
		container.add(resultView.asWidget());
		designResult(container);
	}

	
	private void designResult(final HasWidgets container) {
		rpcService.getDetailsFromSession(new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub
				if (!result) {
					resultView.setMsg();
				}else {
					resultView.deleteSetMsg();
				}
			}
		});
		rpcService.resultFromSessionServer(new AsyncCallback<ResultSearch>() {
			
			@Override
			public void onSuccess(ResultSearch result) {
				// TODO Auto-generated method stub
				HTMLPanel html = new HTMLPanel("<div id='finalResult' class='finalContentResult'><h1>Final Result</h1></div>");
				for (GenericKnowledgeClass res: result.getResult()){
					DivResult unDiv = new DivResult(res,DIV_FINAL_RIS_CONTENT);
					html.add(unDiv, "finalResult");
				}
				container.add(html);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}


}
