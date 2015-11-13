package com.unisa.techadvisor.client;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.unisa.techadvisor.shared.ResultSearch;
import com.unisa.techadvisor.shared.User;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface TechAdvisorService extends RemoteService {
	String signUp(User user) throws UnsupportedEncodingException;
	String logIn(User user);
	void logout();
	
	User loginFromSessionServer();
	
	ArrayList<String> getLanguage();
	void insertLanguage(String lang_list);
	void removeLanguage(ArrayList<String> lang_list);
	
	String setResult(String argument);
	String setSubCategory(String type);
	String setCategory(String category);
	
	String getName();
	ArrayList<String> getLanguages(String languages);
	void setOntology();
	ResultSearch resultFromSessionServer();
	boolean getDetailsFromSession();
	
	
}
