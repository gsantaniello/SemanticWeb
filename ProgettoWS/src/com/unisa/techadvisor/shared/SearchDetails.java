package com.unisa.techadvisor.shared;

public class SearchDetails {
	/**
	 * topi = ecommerce || music
	 * web = website || web application
	 * mobilePLatform = android || iOS || windows Phone 
	 */
	private String topic;
	private String subCategory;
	private String category;
	/**
	 * mi indica lo stato della search
	 * true = ricerca attiva
	 * false = ricerca terminata
	 */
	private boolean status;
	
	public SearchDetails() {
		topic = "";
		subCategory = "";
		category = "";
		status=true;
	}
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * This method is call for know the search details of this object
	 * @return "topic&details"
	 */
	public String searchFor(){
		if (subCategory.equals("")){
			return topic+"&"+category;
		}else if (category.equals("")){
			return topic+"&"+subCategory;
		}else {
			return topic;
		}
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
