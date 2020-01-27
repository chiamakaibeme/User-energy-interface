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
public class UserEnergyNeeds extends User{
    
    private String name;
    private String gender;
    private String ageGroup;
    private double height;
    private double weight;
    private double bmr;
    private PALdata palData;
    
    
    
    //constructor
    public UserEnergyNeeds(String nameInp, String genderInp, String ageGrpInp, double htInp, double weight, double bmr, PALdata palData) {
        super(nameInp, genderInp, ageGrpInp, htInp); //attributes from class User
        
        this.name = nameInp;
        this.gender = genderInp;
        this.ageGroup = ageGrpInp;
        this.height = htInp;
        this.weight = weight;
        this.bmr = bmr;
        this.palData = palData;

    }
    
    //Accessor, mutator methods to obtain values
    public String getName(){
        return name;
    }
    
    public String getGender(){
        return gender;
    }
    
    public String getAgeGroup(){
        return ageGroup;
    }
    
    public double getHeight(){
        return height;
    }
    
    public double setWeight(){
        try{
            if(height>= 1.5 && height <= 2){
                this.weight = weight;
            }
        } catch (IllegalArgumentException e){
            System.out.println("Height value must be between 1.5 and 2.0");
            
            System.out.println("EXCEPTION " + e);
        }
        return weight;

    }
    
    public double getWeight(){
        return weight;
    }
    
    public double getBMR(){
        return bmr;
    } 
    
    public PALdata getPalData(){
        return palData;
    }
    
    //to string method to display stored data
    
    @Override
    public String toString(){
        return String.format("%s %s %s",
            getWeight(),
            getBMR(),
            getPalData()
        );
    }
    
}
