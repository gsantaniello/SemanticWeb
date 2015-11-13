package com.unisa.techadvisor.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class Search1ViewImpl extends Composite implements Search1View {

	private static SearchViewUiBinder uiBinder = GWT
			.create(SearchViewUiBinder.class);

	@UiTemplate("Search1View.ui.xml")
	interface SearchViewUiBinder extends UiBinder<Widget, Search1ViewImpl> {
	}

	public Search1ViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		topic.setStyleName("form-control");
		topic.getElement().setAttribute("placeholder", "Insert your topic");
	}

	@UiField DivElement load;
	@UiField TextArea topic;

	/**
	 * metodo che viene richiamato sul click del pulsante next
	 * prende dalla pagina il topic che l'utente ha inserito
	 */
	@UiField Button next;
	@UiHandler("next")
	void onClickNextButton(ClickEvent e){
		String argument = topic.getValue();
		if (!argument.equals("")){
			if(presenter!=null){
				presenter.next(argument);
				load.setAttribute("style", "display:block; margin-left: 50%;");
			}
		}
	}

	@UiHandler("topic")
	void onClickTopic(FocusEvent e){
		topic.setValue("");
		no_match.setAttribute("style", "display: none");
		load.setAttribute("style", "display: none");
	}

	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return this;
	}
	private Presenter presenter;
	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		this.presenter = presenter;

	}

	@UiField DivElement no_match;

	@Override
	public void setNoMatch() {
		// TODO Auto-generated method stub
		no_match.setAttribute("style", "display: block; color: #fb7b74; font-size: 2em;");
		load.setAttribute("style", "display: none;");
		topic.setValue("");
	}

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		no_match.setAttribute("style", "display: none;");
		load.setAttribute("style", "display:none");
	}

	@Override
	protected void onUnload() {
		// TODO Auto-generated method stub
		topic.setValue("");
	}


}
