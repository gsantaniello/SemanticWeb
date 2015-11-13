package com.unisa.techadvisor.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class Search3_1Event extends GwtEvent<Search3_1EventHandler> {

	public static Type<Search3_1EventHandler> TYPE = new Type<Search3_1EventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Search3_1EventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(Search3_1EventHandler handler) {
		// TODO Auto-generated method stub
		handler.onSearch3_1(this);
	}

}
