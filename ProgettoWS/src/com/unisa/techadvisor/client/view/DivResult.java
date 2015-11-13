package com.unisa.techadvisor.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.unisa.techadvisor.shared.GenericKnowledgeClass;

public class DivResult extends Composite {

	private static TestUiBinder uiBinder = GWT.create(TestUiBinder.class);

	interface TestUiBinder extends UiBinder<Widget, DivResult> {
	}

	public DivResult() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private GenericKnowledgeClass genericKnow;
	private String divClass;
	
	@UiField DivElement divContent;
	
	public DivResult(GenericKnowledgeClass res,String divClass) {
		// TODO Auto-generated constructor stub
		initWidget(uiBinder.createAndBindUi(this));
		this.genericKnow = res;
		this.divClass = divClass;
		createDiv();
	}

	private void createDiv() {
		// TODO Auto-generated method stub
		divContent.setAttribute("class", divClass);
		divContent.setInnerHTML("<ul><li><a href='"+genericKnow.getUrl()+
								"'><img src='"+genericKnow.getLogoLink()+"'>"+
								"<h3>"+genericKnow.getName()+
								"</h3><h4>"+genericKnow.getType()+
								"</h4><h4>"+genericKnow.getLanguage()+
								"</h4><p>"+genericKnow.getDescription()+"</p></a></li></ul>");
		
	}
	
	
}
