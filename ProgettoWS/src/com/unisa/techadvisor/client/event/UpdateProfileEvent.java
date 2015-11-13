package com.unisa.techadvisor.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UpdateProfileEvent extends GwtEvent<UpdateProfileHandler> {
	 public static Type<UpdateProfileHandler> TYPE = new Type<UpdateProfileHandler>();
		
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UpdateProfileHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateProfileHandler handler) {
		// TODO Auto-generated method stub
		handler.onUpdateProfile(this);
		
	}

}
