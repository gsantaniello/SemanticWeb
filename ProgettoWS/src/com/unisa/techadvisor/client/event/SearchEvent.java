package com.unisa.techadvisor.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SearchEvent extends GwtEvent<SearchEventHandler> {

	
	public static Type<SearchEventHandler> TYPE = new Type<SearchEventHandler>();
	@Override
	public Type<SearchEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(SearchEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onSearch(this);
	}

}
