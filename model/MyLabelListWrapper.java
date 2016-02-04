package ru.luka.sendtozpl.model;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlRootElement;


//i'dont know how this is works
@XmlRootElement(name = "myLabels")
public class MyLabelListWrapper {

	private List<MyLabel> myLabels;

   @XmlElement(name = "myLabel")
   

   
   public List<MyLabel>  getMyLabels() {
	   
	   return myLabels;
   }

   public void setMyLabels(List<MyLabel>  myLabels) {
	   this.myLabels = myLabels;
	   }
}

