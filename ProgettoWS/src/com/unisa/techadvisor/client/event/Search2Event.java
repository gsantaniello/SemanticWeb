package com.unisa.techadvisor.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class Search2Event extends GwtEvent<Search2EventHandler> {

	public static Type<Search2EventHandler> TYPE = new Type<Search2EventHandler>();
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Search2EventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(Search2EventHandler handler) {
		// TODO Auto-generated method stub
		handler.onSearch2(this);
	}

}
