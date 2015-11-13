package com.unisa.techadvisor.client.view;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProfileViewImpl extends Composite implements ProfileView {

	private static ProfileViewUiBinder uiBinder = GWT
			.create(ProfileViewUiBinder.class);

	private Presenter presenter;
	private ArrayList<String> listCheck = new ArrayList<String>();

	@UiTemplate("ProfileView.ui.xml")
	interface ProfileViewUiBinder extends UiBinder<Widget, ProfileViewImpl> {
	}

	public ProfileViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		textarea.getElement().setAttribute("placeholder", "Your favourite languages");
	}


	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	
	@UiField Button add;
	TextArea textarea = new TextArea();



	@UiHandler("add")
	void onClickAdd(ClickEvent e){
		if (presenter!=null){
			if (!textarea.getText().equals("")){
				presenter.onAddButton(textarea.getText());
				textarea.setValue("");
				listCheck.clear();
			}
		}
	}

	@Override
	public Widget designTable(ArrayList<String> languages) {

		DockPanel container = new DockPanel();
		DockPanel container2 = new DockPanel();
		container.setStyleName("gwt-container");

		HorizontalPanel hor = new HorizontalPanel();
		Label lab = new Label("Languages");
		lab.setStyleName("gwt-label");
		hor.add(lab);
		container.add(hor, DockPanel.NORTH);
		hor.setStyleName("gwt-thead");

		final VerticalPanel checkboxPanel = new VerticalPanel();
		checkboxPanel.addStyleName("gwt-panelCheckbox");

		for (String l : languages){

			CheckBox checkBox = new CheckBox(l);

			checkBox.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					CheckBox checkBox = (CheckBox)event.getSource();

					if(checkBox.getValue())
						listCheck.add(checkBox.getText());
					else 
						listCheck.remove(checkBox.getText());

					/*String chekkati="";
					for(String s : listCheck){
						chekkati += "/"+s;
					}
					Window.alert(chekkati);*/
				}
			});

			checkBox.addStyleName("gwt-checkbox");
			checkboxPanel.add(checkBox);

		}

		container2.add(checkboxPanel,DockPanel.WEST);
		

		Button delete = null;
		HorizontalPanel panelButton = new HorizontalPanel();
		
		
		if(languages.size()>0){
			delete = new Button("Delete");
			delete.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					presenter.onDeleteButton(listCheck);
					listCheck.clear();
				}
			});
			delete.setStyleName("table_button");
			/*checkboxPanel.add(delete);*/
			panelButton.add(delete);
		}


		VerticalPanel addPanel = new VerticalPanel();

		textarea.setStyleName("gwt-textArea");
		addPanel.add(textarea);
		add.setStyleName("table_button");

		panelButton.add(add);
		panelButton.setStyleName("gwt-panelButton");
		container2.setStyleName("gwt-panelCenter");
		container2.add(addPanel,DockPanel.EAST);
		container.add(container2,DockPanel.CENTER);
		
		container.add(panelButton, DockPanel.SOUTH);
		
		return container;

	}

	public Widget asWidget() {
		return this;
	}

	@Override
	public Widget showLanguages(ArrayList<String> lngs) {
		// TODO Auto-generated method stub
		final VerticalPanel langPanel = new VerticalPanel();
		langPanel.addStyleName("gwt-panelCheckbox");
		for (String lang: lngs){
			Label l = new Label(lang);
			langPanel.add(l);
		}
		return langPanel;
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		//load.setAttribute("style", "display:none");
	}

}


