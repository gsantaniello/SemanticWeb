package com.unisa.techadvisor.shared;

import java.io.Serializable;
public class GenericKnowledgeClass implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7885467165246386327L;
	private String logoLink;
	private String name;
	private String language;
	private String url;
	private String description;
	private String topic;
	private String category;		//Web or Mobile
	private String subcategory;		//Web Application/Site o Andorid/iOS/WindowsPhone
	private String os;
	private String type;
	
	public GenericKnowledgeClass(String logoLink, String name, String language, String url, String description, String topic, String category, String subcategory, String type){
		this.setLogoLink(logoLink);
		this.name=name;
		this.language=language;
		this.url=url;
		this.description=description;
		this.topic=topic;
		this.category=category;
		this.subcategory=subcategory;
		this.type=type;
		this.os="-";
	}
	
	public GenericKnowledgeClass(String logoLink, String name, String lenguage, String url, String description, String topic, String category, String subcategory, String type, String os){
		this.setLogoLink(logoLink);
		this.name=name;
		this.language=lenguage;
		this.url=url;
		this.description=description;
		this.topic=topic;
		this.category=category;
		this.subcategory=subcategory;
		this.type=type;
		this.os=os;
	}
	
	
	public GenericKnowledgeClass() {
		// TODO Auto-generated constructor stub
		this.setLogoLink("");
		this.name="";
		this.language="";
		this.url="";
		this.description="";
		this.topic="";
		this.category="";
		this.subcategory="";
		this.type="";
		this.os="";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getTopic() {
		return topic;
	}


	public void setTopic(String topic) {
		this.topic = topic;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String lenguage) {
		this.language = lenguage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getSubcategory() {
		return subcategory;
	}


	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getLogoLink() {
		return logoLink;
	}

	public void setLogoLink(String logoLink) {
		this.logoLink = logoLink;
	}
}
