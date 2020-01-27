/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userenergyinterfacegui;


/**
 *
 * @author chiamakaibeme
 */
public abstract class User{
    
    private String name;
    private String gender;
    private String ageGroup;
    private double height;
    
    // constructor
    public User(String nameInp, String genderInp, String ageGrpInp, double htInp){
        this.name = nameInp;
        this.gender = genderInp;
        this.ageGroup = ageGrpInp;
        this.height = htInp;
    }
    
    //Accessor, mutator methods to obtain values
    public void setNameInput(String nameInp){
        this.name = nameInp;
    }
    
    public String getNameInput(){
        return name;
    }
    
    public void setGenderInput(String genderInp){
        this.gender = genderInp;
    }
    
    public String getGenderInput(){
        return gender;
    }
    
    public void getAgeGroupInput(String ageGrpInp){
        this.ageGroup = ageGrpInp;
    }
    
    public String getAgeGroupInput(){
        return ageGroup;
    }
    
    public void setHeightInput(double htInp){
        this.height = htInp;
    }
    
    public double getHeightInput(){
        return height;
    }
    
    //to string method to display stored data
    @Override
    public String toString(){
        return String.format("%s:%n %s:%n %s:%n %s:%n",
            "Name: ", getNameInput(),
            "Gender: ", getGenderInput(),
            "Age Group: ", getAgeGroupInput(),
            "Height: ", getHeightInput()
        );
    }
}


