package ru.luka.sendtozpl.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


	

@XmlRootElement(name = "Lengths")
public class MyLengthWrapper {
	
	private List<MyLength> myLength;

	@XmlElement(name = "Length")
	  

	   
	public List<MyLength>  getMyLength() {
		   
		return myLength;
	}

	public void setMyLength(List<MyLength>  myLength) {
		   this.myLength = myLength;
	}
}
