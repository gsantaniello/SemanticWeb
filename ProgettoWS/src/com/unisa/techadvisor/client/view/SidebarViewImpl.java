package com.unisa.techadvisor.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SidebarViewImpl extends Composite implements SidebarView {

	private static SidebarViewUiBinder uiBinder = GWT
			.create(SidebarViewUiBinder.class);
	
	@UiTemplate("SidebarView.ui.xml")
	interface SidebarViewUiBinder extends UiBinder<Widget, SidebarViewImpl> {
	}
	public SidebarViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
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
	
	@UiField DivElement nameContent;

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		nameContent.setInnerHTML(name.toUpperCase());
		
	}
	
	

}
