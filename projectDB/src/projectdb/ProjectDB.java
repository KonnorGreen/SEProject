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

    //transactions
    public transaction createTransaction(){
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
        transaction t2 = new transaction(transactionID);
        mylist.add(t2);
        saveFile(mylist,filename);
        return t2;
    }
    
    public void voidTransaction(int transactionID){
        Integer transid = (Integer)transactionID;
        String filename = "transaction.pdb";
        ArrayList<transaction> mylist = readFile(filename);
        for(transaction t : mylist){
            if(transid == t.getTransID()){
                t.setVoided(true);
            }
        }
        saveFile(mylist, filename);
    }
    
    public transaction getTransaction(int transactionID){
        Integer transid = (Integer)transactionID;
        String filename = "transaction.pdb";
        ArrayList<transaction> mylist = readFile(filename);
        for(transaction t : mylist){
            if(transid == t.getTransID()){
                return t;
            }
        }
        return null;
    }
    
    public void finalizeTransaction(transaction t){
        int index = -1;
        Integer transid = t.getTransID();
        String filename = "transaction.pdb";
        ArrayList<transaction> mylist = readFile(filename);
        for(int i = 0; i < mylist.size();i++){
            transaction temp = mylist.get(i);
            if(transid == temp.getTransID()){
                index = i;
            }
        }
        if(index > -1){
            mylist.remove(index);
        }
        mylist.add(t);
        saveFile(mylist, filename);
    }
    
    public double getSubtotal(transaction t){
        return t.getSub();
    }
    
    public void setSubtotal(transaction t, double sub){
        t.setSub(sub);
    }
    
    public double getTax(transaction t){
        return t.getTax();
    }
    
    public void setTax(transaction t, double tax){
        t.setTax(tax);
    }
    
    public double getTotal(transaction t){
        return t.getTotal();
    }
    
    public void setTotal(transaction t, double total){
        t.setTotal(total);
    }
    
    public int getPointsRedeemed(transaction t){
        return t.getPointsRedeemed();
    }
    
    public void setPointsRedeemed(transaction t, int points){
        t.setPointsRedeemed(points);
    }
    
    public int getPointsEarned(transaction t){
        return t.getPointsEarned();
    }
    
    public void setPointsEarned(transaction t, int points){
        t.setPointsEarned(points);
    }
    
    



    //rewards
    public rewards enroll(int phone){
        String filename = "rewards.pdb";
        ArrayList<rewards> mylist = readFile(filename);
        for(rewards r : mylist){
            if(r.getPhone() == phone){
                return r;
            }
        }
        rewards r2 = new rewards(phone);
        mylist.add(r2);
        saveFile(mylist,filename);
        return r2;
    }

    


    //products




    //merchants





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
    




    public static String getDescription(int productID){
        String output = "no depsoit, no return";
        return output;
    }

    public static double getPrice(int productID){
        return 0.0;
    }


}
    



