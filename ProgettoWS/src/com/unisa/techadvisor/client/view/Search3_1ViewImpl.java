package com.unisa.techadvisor.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Search3_1ViewImpl extends Composite implements Search3_1View {

	private static Search2ViewUiBinder uiBinder = GWT
			.create(Search2ViewUiBinder.class);

	@UiTemplate("Search3_1View.ui.xml")
	interface Search2ViewUiBinder extends UiBinder<Widget, Search3_1ViewImpl> {
	}

	public Search3_1ViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private Presenter presenter;
	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		this.presenter = presenter;
	}

	@UiField DivElement load;
	
	@UiField Button webSite;
	@UiHandler("webSite")
	void onClickWebSite(ClickEvent e){
		if (presenter !=null){
			presenter.onWebSiteClicked();
			load.setAttribute("style", "display:block; margin-left: 50%;");
		}
	}
	
	@UiField Button webApplication;
	@UiHandler("webApplication")
	void onClickWebApp(ClickEvent e){
		if (presenter != null){
			presenter.onWebAppClicked();
			load.setAttribute("style", "display:block; margin-left: 50%;");
		}
	}
	
	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		load.setAttribute("style", "display:none");
	}
	
}
