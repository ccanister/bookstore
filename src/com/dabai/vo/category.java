package com.dabai.vo;

public class category {
	private String id;
	private String description;
	private String name;
	private boolean iscommond;
	public boolean isIscommond() {
		return iscommond;
	}
	public void setIscommond(boolean iscommond) {
		this.iscommond = iscommond;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
