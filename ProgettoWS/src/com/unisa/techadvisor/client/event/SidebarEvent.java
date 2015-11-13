package com.unisa.techadvisor.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SidebarEvent extends GwtEvent<SidebarEventHandler> {
	 public static Type<SidebarEventHandler> TYPE = new Type<SidebarEventHandler>();
	 
	 
	@Override
	public Type<SidebarEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(SidebarEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onSidebar(this);
	}

}
