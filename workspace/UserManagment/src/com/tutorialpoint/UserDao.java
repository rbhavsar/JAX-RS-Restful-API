package com.tutorialpoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
   public List<User> getAllUsers(){
      List<User> userList = null;
      try {
         File file = new File("Users.txt");
         if (!file.exists()) {
            User user = new User(1, "Mahesh", "Teacher");
            userList = new ArrayList<User>();
            userList.add(user);
            saveUserList(userList);		
         }
         else{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            userList = (List<User>) ois.readObject();
            ois.close();
         }
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }		
      return userList;
   }

   public User getUser(int id){
      List<User> users = getAllUsers();

      for(User user: users){
         if(user.getId() == id){
            return user;
         }
      }
      return null;
   }
   
   public List<User> getUsersPaginated(int start,int size){
	   	 List<User> users = getAllUsers();
	   	 System.out.println("start :"+start+"size :"+size);
	   	 if(start+size>users.size()) {
	   		 return users;
	   	 }
	   	 if(start==0 && size==0) {
	   		 return users;
	   	 }
	   	 
	   	System.out.println("sublist :"+users.subList(start, start+size));
		return users.subList(start, start+size);
   	}
   
   public User getUserBy(int id,String profession) {
	   List<User> users=getAllUsers();
	   System.out.println("In getUserBy...");
	   for(User user:users) {
		 if(user.getId()==id && user.getProfession().equalsIgnoreCase(profession)) {
		 System.out.println("Codition true");
		 return user;
	  }
	}
	 return null;
   }

   public int addUser(User pUser){
      List<User> userList = getAllUsers();
      boolean userExists = false;
      for(User user: userList){
         if(user.getId() == pUser.getId()){
            userExists = true;
            System.out.println("User is already exist for id "+pUser.getId());
            break;
         }
      }		
      if(!userExists){
         userList.add(pUser);
         saveUserList(userList);
         return 1;
      }
      return 0;
   }

   public int updateUser(User pUser){
      List<User> userList = getAllUsers();

      for(User user: userList){
         if(user.getId() == pUser.getId()){
            int index = userList.indexOf(user);			
            userList.set(index, pUser);
            saveUserList(userList);
            return 1;
         }
      }		
      return 0;
   }

   public int deleteUser(int id){
      List<User> userList = getAllUsers();

      for(User user: userList){
         if(user.getId() == id){
            int index = userList.indexOf(user);			
            userList.remove(index);
            saveUserList(userList);
            return 1;   
         }
      }		
      return 0;
   }

   private void saveUserList(List<User> userList){
      try {
         File file = new File("Users.txt");
         FileOutputStream fos;

         fos = new FileOutputStream(file);

         ObjectOutputStream oos = new ObjectOutputStream(fos);		
         oos.writeObject(userList);
         oos.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}