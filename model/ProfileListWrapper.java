package ru.luka.sendtozpl.model;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "profiles")
public class ProfileListWrapper {

	private List<Profile> profiles;

   @XmlElement(name = "profile")
   public List<Profile> getProfile() {
	    return profiles;
   }

   public void setProfile(List<Profile> profiles) {
	   this.profiles = profiles;
	    }
}
