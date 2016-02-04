package ru.luka.sendtozpl.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "colors")
public class MyColorWrapper {

	private List<MyColor> myColors;

   @XmlElement(name = "color")
   public List<MyColor>  getMyColors() {
	   return myColors;
   }

   public void setMyColors(List<MyColor>  myColors) {
	   this.myColors = myColors;
	   }
}
