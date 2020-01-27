package userenergyinterfacegui;

/**
 *
 * @author 12076589
 */
public class BMRdata {
    
    private final String ageGroup;
    private final String gender;
    private final double height;
    private final double weight;
    private final double bmr;
    
    
    // create constructor
    public BMRdata(String ageGp, String gndr, double ht, double wt, double bmr){
        this.ageGroup = ageGp;
        this.gender = gndr;
        this.height = ht;
        this.weight = wt;
        this.bmr = bmr;
        
    }
    
    //Accessor, mutator methods to obtain values
    public String getAgeGroup(){
        return ageGroup;
    }
    
    public String getGender(){
        return gender;
    }
    
    public double getHeight(){
        return height;
    }
     
    public double getWeight(){
        return weight;
    }
    
    public double getBMR(){
        return bmr;
    } 
    
    //to string method to display stored data
    @Override
    public String toString(){
        return String.format("%s %s %s %s %s",
             getAgeGroup(),
            getGender(),
            getHeight(),
            getWeight(),
            getBMR()
        );
    }
}
