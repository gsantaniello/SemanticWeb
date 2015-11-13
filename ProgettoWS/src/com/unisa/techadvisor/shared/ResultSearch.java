package com.unisa.techadvisor.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultSearch implements Serializable{
	
	private static final long serialVersionUID = 4258097115768842479L;
	
	private ArrayList<GenericKnowledgeClass> result = new ArrayList<GenericKnowledgeClass>();
	
	public ResultSearch() {
	}

	public ResultSearch(ArrayList<GenericKnowledgeClass> result) {
		this.result = result;
	}

	public ArrayList<GenericKnowledgeClass> getResult() {
		return result;
	}

	public void setResult(ArrayList<GenericKnowledgeClass> result) {
		this.result = result;
	}
	
	
}
