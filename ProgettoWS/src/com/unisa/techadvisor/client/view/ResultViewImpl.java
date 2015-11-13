package com.unisa.techadvisor.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ResultViewImpl extends Composite implements ResultView {

	private static ResultViewUiBinder uiBinder = GWT
			.create(ResultViewUiBinder.class);

	@UiTemplate("ResultView.ui.xml")
	interface ResultViewUiBinder extends UiBinder<Widget, ResultViewImpl> {
	}

	public ResultViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private Presenter presenter;

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	public Widget asWidget() {
		return this;
	}

	@UiField DivElement prematureMsg;
	
	
	@Override
	public void setMsg() {
		// TODO Auto-generated method stub
		prematureMsg.setAttribute("style", "display: block; color: #fb7b74; font-size: 2em;");
		
	}

	@Override
	public void deleteSetMsg() {
		// TODO Auto-generated method stub
		prematureMsg.setAttribute("style", "display: none;");
		
	}

}
