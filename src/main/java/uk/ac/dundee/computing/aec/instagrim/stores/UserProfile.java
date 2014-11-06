/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

/**
 *
 * @author TheFractal
 */
public class UserProfile {
    //boolean logedin=false;
    String Username=null;
    String first_name=null;
    String last_name=null;
    //String email=null;
    public void UserProfile(){
        
    }
    
    public void setUser(String name, String first_name, String last_name){
        this.Username=name;
        this.first_name=first_name;
        this.last_name=last_name;
    }
    public String getUsername(){
        return Username;
    }
    public void setFirstName(String first_name){
        this.first_name=first_name;
    }
    public String getFirstName(){
        System.out.println(first_name);
        return first_name;
    }
    public void setLastName(String last_name){
        this.last_name=last_name;
    }
    public String getLastName(){
        System.out.println(last_name);
        return last_name;
    }
    
    /*public void setLoginState(boolean logedin){
        this.logedin=logedin;
    }
    public boolean getlogedin(){
        return logedin;
    }*/
}
