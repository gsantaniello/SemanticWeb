package com.unisa.techadvisor.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ResultEvent extends GwtEvent<ResultEventHandler> {

	public static Type<ResultEventHandler> TYPE = new Type<ResultEventHandler>();
	
	@Override
	public Type<ResultEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ResultEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onResult(this);
	}

}
