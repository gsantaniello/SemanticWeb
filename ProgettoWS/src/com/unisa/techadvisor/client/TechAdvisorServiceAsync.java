package com.unisa.techadvisor.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.unisa.techadvisor.shared.ResultSearch;
import com.unisa.techadvisor.shared.User;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface TechAdvisorServiceAsync {
	
	void signUp(User user, AsyncCallback<String> callback);

	void logIn(User user, AsyncCallback<String> callback);

	void getLanguage(AsyncCallback<ArrayList<String>> callback);

	void setResult(String argument, AsyncCallback<String> callback);

	void loginFromSessionServer(AsyncCallback<User> callback);
	
	void insertLanguage(String lang_list, AsyncCallback<Void> callback);

	void removeLanguage(ArrayList<String> lang_list,
			AsyncCallback<Void> callback);

	void setSubCategory(String type, AsyncCallback<String> callback);

	void logout(AsyncCallback<Void> callback);

	void setOntology(AsyncCallback<Void> callback);

	void getName(AsyncCallback<String> callback);

	void getLanguages(String languages,
			AsyncCallback<ArrayList<String>> callback);

	void resultFromSessionServer(AsyncCallback<ResultSearch> callback);

	void setCategory(String category, AsyncCallback<String> callback);

	void getDetailsFromSession(AsyncCallback<Boolean> callback);
}