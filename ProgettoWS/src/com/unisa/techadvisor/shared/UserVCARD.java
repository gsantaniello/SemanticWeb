package com.unisa.techadvisor.shared;


import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.vocabulary.VCARD;

public class UserVCARD extends VCARD {

	private static final String URI = "http://www.w3.org/2001/vcard-rdf/3.0#";
	
	private static final Model model = ModelFactory.createDefaultModel();
	
	public static final Property LANGUAGE = model.createProperty(URI, "LANGUAGE");
	public static final Property PASSWORD = model.createProperty(URI, "PASSWORD");
	
}
