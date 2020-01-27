/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userenergyinterfacegui;

import java.util.ArrayList;


public class EnergyData {
    private BMRdata bmrObj;
    private ArrayList<PALdata> palCorrArr;
    
    //create constructor
    public EnergyData(BMRdata bmrObj, ArrayList<PALdata> palCorrArr){
        this.bmrObj = bmrObj;
        this.palCorrArr = palCorrArr;

    }
    
    //Accessor, mutator methods to obtain values
   
    public BMRdata getBMRdata(){
        return bmrObj;
    }

    public ArrayList<PALdata> getPalData(){
        return palCorrArr;
    }
    
//    to string method to display stored data
    @Override
    public String toString(){
        return String.format("%s %s", getBMRdata(), getPalData());
    }
    
}
