package com.unisa.techadvisor.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String password;
	private ArrayList<String> language;
	
	public User(){
		
	}
	public User(String name, String email, String password, ArrayList<String> language){
		this.name = name;
		this.email = email;
		this.password = password;
		this.language = new ArrayList<String>();
		this.language = language;
	}
	
	public User(String email, String password){
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<String> getLanguage() {
		return language;
	}

	public void setLanguage(ArrayList<String> language) {
		this.language = language;
	}
	
	

}
