package ru.luka.sendtozpl.model;

import java.util.HashMap;
import java.util.Map;

public class MyColor {
	
	
	private Map<String,String> map = new HashMap<String, String>();
	private String typeLabel = new String();
	
	public String getTypeLabel () {
		return typeLabel;
	}
	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String toString(){
		return(map.toString());
	}
}
