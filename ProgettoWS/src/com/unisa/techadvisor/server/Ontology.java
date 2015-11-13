package com.unisa.techadvisor.server;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.unisa.techadvisor.shared.GenericKnowledgeClass;
import com.unisa.techadvisor.shared.User;

public interface Ontology {
	public String signUp(User user) throws UnsupportedEncodingException;
	public String logIn(User user);
	public ArrayList<String> getLanguage(String mail);
	public ArrayList<GenericKnowledgeClass> searchInference(String email, String topic, String category, String subcategory);
	public void insertLanguage(User user, ArrayList<String> lang_list);
	public void removeLanguage(User user, ArrayList<String> lang_list);
	String getName(String email);
}
