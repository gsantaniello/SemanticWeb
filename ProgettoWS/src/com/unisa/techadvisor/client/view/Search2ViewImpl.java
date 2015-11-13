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

public class Search2ViewImpl extends Composite implements Search2View {

	private static Search2ViewUiBinder uiBinder = GWT
			.create(Search2ViewUiBinder.class);

	@UiTemplate("Search2View.ui.xml")
	interface Search2ViewUiBinder extends UiBinder<Widget, Search2ViewImpl> {
	}

	public Search2ViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField DivElement load;

	@UiField Button web;
	@UiHandler("web")
	void onClickWeb(ClickEvent e){
		if (presenter!=null){
			presenter.web();
			load.setAttribute("style", "display:block; margin-left: 50%;");
		}
	}
	
	@UiField Button mobile;
	@UiHandler("mobile")
	void onClickMobile(ClickEvent e){
		if (presenter!=null){
			presenter.mobile();
			load.setAttribute("style", "display:block; margin-left: 50%;");
		}
	}
	
	private Presenter presenter;
	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		this.presenter = presenter;
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
