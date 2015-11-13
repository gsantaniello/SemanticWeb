package com.unisa.techadvisor.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ProfileEvent extends GwtEvent<ProfileEventHandler>{

	 public static Type<ProfileEventHandler> TYPE = new Type<ProfileEventHandler>();

	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ProfileEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(ProfileEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onProfile(this);
	}
	
	

}
