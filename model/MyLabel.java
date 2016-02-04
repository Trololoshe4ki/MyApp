package ru.luka.sendtozpl.model;

import java.util.Map;
import java.util.HashMap;
import javax.xml.bind.annotation.*;



public class MyLabel {
	
			
	private Map<String,String> map = new HashMap<String, String>();
	
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> testMap) {
		this.map = testMap;
	}
	public String toString(){
		return(map.toString());
	}
}
