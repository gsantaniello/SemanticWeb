package com.unisa.techadvisor.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface UpdateProfileHandler extends EventHandler {
	void onUpdateProfile(UpdateProfileEvent up);
}
