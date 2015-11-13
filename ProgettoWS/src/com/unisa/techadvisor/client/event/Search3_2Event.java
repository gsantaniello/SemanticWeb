package com.unisa.techadvisor.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class Search3_2Event extends GwtEvent<Search3_2EventHandler> {

	public static Type<Search3_2EventHandler> TYPE = new Type<Search3_2EventHandler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Search3_2EventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(Search3_2EventHandler handler) {
		// TODO Auto-generated method stub
		handler.onSearch3_2(this);
	}

}
