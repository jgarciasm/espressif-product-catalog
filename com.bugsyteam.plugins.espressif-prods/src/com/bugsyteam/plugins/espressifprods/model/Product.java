package com.bugsyteam.plugins.espressifprods.model;

public class Product {
	
	private String name = "";
	private String Type = "?";
	private String Description = "";
	
	public Product(String name, String type, String description) {
		super();
		this.name = name;
		Type = type;
		Description = description;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean contains(String value) {
		if(name.toLowerCase().contains(value.toLowerCase()) || Type.toLowerCase().contains(value.toLowerCase()) || Description.toLowerCase().contains(value.toLowerCase()))
			return true;
		return false;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	
	

}
