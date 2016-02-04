package ru.luka.sendtozpl.model;

import java.util.HashMap;
import java.util.Map;

public class MyLength {

	private Map<Integer, String> map = new HashMap <Integer, String>();
	private String typeLength = new String();
	
	public String getTypeLabel () {
		return typeLength;
	}
	public void setTypeLabel(String typeLength) {
		this.typeLength = typeLength;
	}
	public Map<Integer, String> getMap() {
		return map;
	}
	public void setMap(Map<Integer, String> map) {
		this.map = map;
	}
	public String toString(){
		return(map.toString());
	}
}
