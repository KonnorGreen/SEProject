/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdb;

/**
 *
 * @author anazo
 */
public class settings {
    double pointsPerDollar;
    double dollarsPerPoint;
    String profileName;
    
    public void settings(String name){
        profileName = name;
    }
    
    public String getProfileName(String name){
        return profileName;
    }

    public void setPointsPerDollar(double pointsPerDollar){
        this.pointsPerDollar = pointsPerDollar;
    }
    
    public double getPointsPerDollar(){
        return pointsPerDollar;
    }
    
    public void setDollarsPerPoint(double dollarsPerPoint){
        this.dollarsPerPoint = dollarsPerPoint;
    }
    
    public double getDollarsPerPoint(){
        return dollarsPerPoint;
    }
    
}
