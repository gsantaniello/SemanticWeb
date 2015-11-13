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

public class Search3_2ViewImpl extends Composite implements Search3_2View{

	private static Search3_2ViewUiBinder uiBinder = GWT
			.create(Search3_2ViewUiBinder.class);

	@UiTemplate("Search3_2View.ui.xml")
	interface Search3_2ViewUiBinder extends UiBinder<Widget, Search3_2ViewImpl> {
	}

	public Search3_2ViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private Presenter presenter;
	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		this.presenter = presenter;
	}
	
	@UiField DivElement load;
	
	@UiField Button android;
	@UiHandler("android")
	void onClickAndroid(ClickEvent e){
		if (presenter!=null){
			presenter.onAndroidClicked();
			load.setAttribute("style", "display:block; margin-left: 50%;");
		}
	}
	
	@UiField Button ios;
	@UiHandler("ios")
	void onClickIos(ClickEvent e){
		if (presenter!=null){
			presenter.onIOSClicked();
			load.setAttribute("style", "display:block; margin-left: 50%;");
		}
	}
	
	@UiField Button wphone;
	@UiHandler("wphone")
	void onClickWphone(ClickEvent e){
		if (presenter!=null){
			presenter.onWindowsPhoneClicked();
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
