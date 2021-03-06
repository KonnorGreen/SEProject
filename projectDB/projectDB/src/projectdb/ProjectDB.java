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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ProjectDB {
    merchant loadedMerchant;
    product loadedProduct;
    rewards loadedRewards;
    settings loadedSettings;
    transaction loadedTransaction;
    
    

    //transactions
    public boolean transactionLoaded(int transID){
        if(loadedTransaction.getTransID() == transID){
            return true;
        }
        return false;
    }
    
    public void createTransaction(){
        int transactionID;
        String filename = "transaction.pdb";
        ArrayList<transaction> mylist = readFile(filename);
        transactionID = -1;
        for (transaction t : mylist){
            if(t.getTransID() > transactionID){
                transactionID = t.getTransID();
            }
        }
        transactionID++;
        loadedTransaction = new transaction(transactionID);
        saveTransaction();
    }
    
    public void loadTransaction(int transID){
        ArrayList<transaction> temp = readFile("transaction.pdb");
        for(transaction t : temp){
            if(transID == t.getTransID()){
                loadedTransaction = t;
            }
        }
    }
    
    public void saveTransaction(){
        ArrayList<transaction> temp = readFile("transaction.pdb");
        int transID = loadedTransaction.getTransID();
        int index = -1;
        for(int i = 0; i < temp.size();i++){
            if(transID == temp.get(i).getTransID()){
                index = i;
            }
        }
        if(index > 0){
            temp.remove(index);
        }
        temp.add(loadedTransaction);
        saveFile(temp, "transaction.pdb");
    }
    
    
    
    public void voidTransaction(int transactionID){
        if(!transactionLoaded(transactionID)){
            loadTransaction(transactionID);
        }
        loadedTransaction.setVoided(true);
        saveTransaction();
    }
    
    public double getSubtotal(int transactionID){
        if(!transactionLoaded(transactionID)){
            loadTransaction(transactionID);
        }
        return loadedTransaction.getSub();
    }
    
    public void setSubtotal(int transactionID, double sub){
        if(!transactionLoaded(transactionID)){
            loadTransaction(transactionID);
        }
        loadedTransaction.setSub(sub);
    }
    
    public double getTax(int transactionID){
        if(!transactionLoaded(transactionID)){
            loadTransaction(transactionID);
        }
        return loadedTransaction.getTax();
    }
    
    public void setTax(int transactionID, double tax){
        if(!transactionLoaded(transactionID)){
            loadTransaction(transactionID);
        }
        loadedTransaction.setTax(tax);
    }
    
    public double getTotal(int transactionID){
        if(!transactionLoaded(transactionID)){
            loadTransaction(transactionID);
        }
        return loadedTransaction.getTotal();
    }
    
    public void setTotal(int transactionID, double total){
        if(!transactionLoaded(transactionID)){
            loadTransaction(transactionID);
        }
        loadedTransaction.setTotal(total);
    }
    
    public int getPointsRedeemed(int transactionID){
        if(!transactionLoaded(transactionID)){
            loadTransaction(transactionID);
        }
        return loadedTransaction.getPointsRedeemed();
    }
    
    public void setPointsRedeemed(int transactionID, int points){
        if(!transactionLoaded(transactionID)){
            loadTransaction(transactionID);
        }
        loadedTransaction.setPointsRedeemed(points);
    }
    
    public int getPointsEarned(int transactionID){
        if(!transactionLoaded(transactionID)){
            loadTransaction(transactionID);
        }
        return loadedTransaction.getPointsEarned();
    }
    
    public void setPointsEarned(int transactionID, int points){
        if(!transactionLoaded(transactionID)){
            loadTransaction(transactionID);
        }
        loadedTransaction.setPointsEarned(points);
    }
    
    



    //rewards
    public boolean rewardsExists(ArrayList<rewards> temp, int phone){
        for(rewards r : temp){
            if(phone == r.getPhone()){
                return true;
            }
        }
        return false;
    }
    
    public void rewardsEnroll(int phone){
        ArrayList<rewards> temp = readFile("rewards.pdb");
        if(rewardsExists(temp, phone)){
            loadRewards(phone);
        }
        else{
            loadedRewards = new rewards(phone);
            saveRewards();
        }
    }

    public boolean rewardsLoaded(int phone){
        if(loadedRewards.getPhone() == phone){
            return true;
        }
        return false;
    }
    
    public void loadRewards(int phone){
        ArrayList<rewards> temp = readFile("rewards.pdb");
        for(rewards r : temp){
            if(phone == r.getPhone()){
                loadedRewards = r;
            }
        }
    }
    
    public void saveRewards(){
        ArrayList<rewards> temp = readFile("rewards.pdb");
        int phone = loadedRewards.getPhone();
        int index = -1;
        for(int i = 0; i < temp.size(); i++){
            if(phone == temp.get(i).getPhone()){
                index = i;
            }
        }
        if(index > -1){
            temp.remove(index);
        }
        temp.add(loadedRewards);
        saveFile(temp, "rewards.pdb");
    }
    
    public void addTransaction(int phone, int transactionID){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        loadedRewards.addTransaction(transactionID);
    }
    
    public double getBalance(int phone){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        return loadedRewards.getBalance();
    }

    public String getCustName(int phone){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        return loadedRewards.getCustName();
    }
    
    public ArrayList<Integer> getTransactionHistory(int phone){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        return loadedRewards.getTransactionHistory();
    }
    
    public void removeTransaction(int phone, int transactionID){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        loadedRewards.removeTransaction(transactionID);
    }
    
    public void resetRewards(int phone){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        loadedRewards.reset();
    }
    
    public void setRewardsBalance(int phone, double balance){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        loadedRewards.setBalance(balance);
    }
    
    public void setCustName(int phone, String name){
            if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
            loadedRewards.setCustName(name);
    }

    //products
    
    
    
//temporary to eliminate errors    
public static String getDescription(int productID){
    return null;
}

public static double getPrice(int productID){
    return 0.0;
}




    //merchants



    //settings
    public boolean settingsExists(String profileName){
        ArrayList<settings> temp = readFile("settings.pdb");
        for(settings s : temp){
            if(profileName.equals(s.getProfileName())){
                return true;
            }
        }
        return false;
    }
    
    public boolean settingsExists(ArrayList<settings> temp, String profileName){
        for(settings s : temp){
            if(profileName.equals(s.getProfileName())){
                return true;
            }
        }
        return false;
    }
    
    public void createSettings(String profileName){
        ArrayList<settings> temp = readFile("settings.pdb");
        if(settingsExists(temp, profileName)){
            loadSettings(profileName);
        }
        else{
            loadedSettings = new settings(profileName);
            saveSettings();
        }
    }
    
    public void loadSettings(String profileName){
        ArrayList<settings> temp = readFile("settings.pdb");
        for(settings s : temp){
            if(profileName.equals(s.getProfileName())){
                loadedSettings = s;
            }
        }
    }
    
    public void saveSettings(){
        ArrayList<settings> temp = readFile("settings.pdb");
        String name = loadedSettings.getProfileName();
        int index = -1;
        for(int i = 0; i < temp.size();i++){
            if(name.equals(temp.get(i).getProfileName())){
                index = i;
            }
        }
        if(index > 0){
            temp.remove(index);
        }
        temp.add(loadedSettings);
        saveFile(temp, "settings.pdb");
    }

    public void setPointsPerDollar(String profileName, double PointsPerDollar){
        if(!profileName.equals(loadedSettings.getProfileName())){
            loadSettings(profileName);
        }
        loadedSettings.setPointsPerDollar(PointsPerDollar);
        saveSettings();
    }
    
    public double getPointsPerDollar(String profileName){
        if(!profileName.equals(loadedSettings.getProfileName())){
            loadSettings(profileName);
        }
        return loadedSettings.getPointsPerDollar();
    }
    
    public void setDollarsPerPoint(String profileName, double dollarsPerPoint){
            if(!profileName.equals(loadedSettings.getProfileName())){
            loadSettings(profileName);
        }
            loadedSettings.setDollarsPerPoint(dollarsPerPoint);
            saveSettings();
    }
    
    public double getDollarsPerPoint(String profileName){
        if(!profileName.equals(loadedSettings.getProfileName())){
            loadSettings(profileName);
        }
        return loadedSettings.getDollarsPerPoint();
    }
    
    
    
            
    

    //other
    public ArrayList readFile(String filename){
        ArrayList temp = null;
        File myfile = new File(filename);
        if(!myfile.exists()){
            try{ 
                myfile.createNewFile();
            }
            catch(IOException e){
                System.out.println("An error occurred.");
            }
        }
        try{
            FileInputStream fis = new FileInputStream(myfile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            temp = (ArrayList)ois.readObject();
            ois.close();
        }
        catch(IOException | ClassNotFoundException e){
            System.out.println("An error occurred.");
        }
        return temp;
    }
    
    public void saveFile(ArrayList mylist, String fileName){
        try{
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(mylist);
            out.close();
        }
            catch(IOException e){
                System.out.println("An error occurred.");
            }
    }
    
    public boolean isVoided(transaction t){
        return t.isVoided();
    }
    

    
    


}
    



