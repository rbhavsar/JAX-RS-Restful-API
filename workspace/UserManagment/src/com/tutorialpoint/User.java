package com.tutorialpoint;

/*
 * https://www.tutorialspoint.com/restful/
 * Follow above link steps to setup tomcat , postman 
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "user")
public class User implements Serializable {

   private static final long serialVersionUID = 1L;
   private int id;
   private String name;
   private String profession;
   //private Map<Long,Comment> comments=new HashMap<>();
   List<Link> links = new ArrayList<>();
   

   public List<Link> getLinks() {
	return links;
}

   public void setLinks(List<Link> links) {
	this.links = links;
   }
   
   public void addLink(String url,String rel) {
	   System.out.println("addlink, url:"+url+" And rel:"+rel);
	   Link link = new Link();
	   link.setLink(url);
	   link.setRel(rel);
	   System.out.println("g1 :"+link.getLink());
	   System.out.println("g2 :"+link.getRel());
	   try {
	   links.add(link);}
	   catch(Exception e){
		   e.printStackTrace();
	   }
	   System.out.println("links :"+links);
	   
   }

public User(){}

   public User(int id, String name, String profession){
      this.id = id;
      this.name = name;
      this.profession = profession;
   }

   public int getId() {
      return id;
   }
   @XmlElement
   public void setId(int id) {
      this.id = id;
   }
   public String getName() {
      return name;
   }
   @XmlElement
      public void setName(String name) {
      this.name = name;
   }
   public String getProfession() {
      return profession;
   }
   @XmlElement
   public void setProfession(String profession) {
      this.profession = profession;
   }	

   @Override
   public boolean equals(Object object){
      if(object == null){
         return false;
      }else if(!(object instanceof User)){
         return false;
      }else {
         User user = (User)object;
         if(id == user.getId()
            && name.equals(user.getName())
            && profession.equals(user.getProfession())
         ){
            return true;
         }			
      }
      return false;
   }

@Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", profession=" + profession + "]";
}	
}