package ru.luka.sendtozpl.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Profile {

	private final StringProperty type;
	private final StringProperty fullName;
	
	
	public Profile () {
		this(null, null);
	}
	 /**
     * Constructor with some initial data.
     * 
     * @param typeProfile
     * @param fullNameProfile
     */
	
	public Profile (String type, String fullName) {
		this.type = new SimpleStringProperty(type);
		this.fullName = new SimpleStringProperty(fullName);

	}


	
	public String getType() {
		return type.get();
		
	}
	public void setType(String type) {
		this.type.set(type);
	}
	
	public StringProperty  typeProperty() {
		return type;
	}
	public String getFullName() {
		return fullName.get();
		
	}
	public void setFullName(String fullName) {
		this.fullName.set(fullName);
	}
	
	public StringProperty  fullNameProperty() {
		return fullName;
	}
}
