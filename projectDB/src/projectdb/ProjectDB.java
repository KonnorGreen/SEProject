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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
//import java.io.FileNotFoundException;
//import java.util.Scanner;

public class ProjectDB {
    public static void main(String[] args) {
        int id = createProduct();
        setImageFolder(id,"test");
        setImageName(id,"this.jpg");
        System.out.println(getImagePath(id));
        System.out.println(loadedProduct.imageExists(getImagePath(id),getImageName(id)));
        System.out.println(System.getProperty("file.separator"));
        System.out.print("Hello world!\n");
    }
    static merchant loadedMerchant;
    static product loadedProduct;
    static rewards loadedRewards;
    static settings loadedSettings;
    static transaction loadedTransaction;
    
    

    //transactions
    public boolean transactionLoaded(int transID){
        if(loadedTransaction.getTransID() == transID){
            return true;
        }
        return false;
    }
    
    public static int createTransaction(){
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
        return transactionID;
    }
    
    public void loadTransaction(int transID){
        ArrayList<transaction> temp = readFile("transaction.pdb");
        for(transaction t : temp){
            if(transID == t.getTransID()){
                loadedTransaction = t;
            }
        }
    }
    
    public static void saveTransaction(){
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
    public static boolean rewardsExists(ArrayList<rewards> temp, long phone){
        if(temp == null){
            return false;
        }
        for(rewards r : temp){
            if(phone == r.getPhone()){
                return true;
            }
        }
        return false;
    }
    
    public static void rewardsEnroll(long phone){
        ArrayList<rewards> temp = readFile("rewards.pdb");
        if(rewardsExists(temp, phone)){
            loadRewards(phone);
        }
        else{
            loadedRewards = new rewards(phone);
            saveRewards();
        }
    }

    public boolean rewardsLoaded(long phone){
        return loadedRewards.getPhone() == phone;
    }
    
    public static void loadRewards(long phone){
        ArrayList<rewards> temp = readFile("rewards.pdb");
        temp.stream().filter((r) -> (phone == r.getPhone())).forEachOrdered((r) -> {
            loadedRewards = r;
        });
    }
    
    public static void saveRewards(){
        ArrayList<rewards> temp = readFile("rewards.pdb");
        long phone = loadedRewards.getPhone();
        int index = -1;
        if(temp != null){
            System.out.println(temp.size());
            for(int i = 0; i < temp.size(); i++){
                if(phone == temp.get(i).getPhone()){
                    index = i;
                }
            }
            if(index > -1){
                temp.remove(index);
            }
            temp.add(loadedRewards);
        }
//        System.out.println(loadedRewards == null);
        temp = new ArrayList<>();
        temp.add(loadedRewards);
        saveFile(temp, "rewards.pdb");
    }
    
    public void addTransaction(long phone, int transactionID){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        loadedRewards.addTransaction(transactionID);
    }
    
    public double getBalance(long phone){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        return loadedRewards.getBalance();
    }

    public String getCustName(long phone){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        return loadedRewards.getCustName();
    }
    
    public ArrayList<Integer> getTransactionHistory(long phone){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        return loadedRewards.getTransactionHistory();
    }
    
    public void removeTransaction(long phone, int transactionID){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        loadedRewards.removeTransaction(transactionID);
    }
    
    public void resetRewards(long phone){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        loadedRewards.reset();
    }
    
    public void setRewardsBalance(long phone, double balance){
        if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
        loadedRewards.setBalance(balance);
    }
    
    public void setCustName(long phone, String name){
            if(phone != loadedRewards.getPhone()){
            loadRewards(phone);
        }
            loadedRewards.setCustName(name);
    }

    //products
    public static int createProduct(){
        int productID;
        String fileName = "product.pdb";
        ArrayList<product> mylist = readFile(fileName);
        productID = -1;
        if(mylist != null){
            for (product p : mylist){
                if(p.getProductID() > productID){
                    productID = p.getProductID();
                }
            }
        }
        productID++;
        loadedProduct = new product(productID);
        saveProduct();
        return productID;
    }
    
    public static void loadProduct(int productID){
        ArrayList<product> temp = readFile("product.pdb");
        temp.stream().filter((p) -> (productID == p.getProductID())).forEachOrdered((p) -> {
            loadedProduct = p;
        });
    }
    
    public static void saveProduct(){
        ArrayList<product> temp = readFile("product.pdb");
        int productID = loadedProduct.getProductID();
        int index = -1;
        if(temp != null){
            for(int i = 0; i < temp.size(); i++){
                if(productID == temp.get(i).getProductID()){
                    index = i;
                }
            }
            if(index > -1){
                temp.remove(index);
            }
            temp.add(loadedProduct);
        }
        temp = new ArrayList<>();
        temp.add(loadedProduct);
        saveFile(temp, "product.pdb");
    }
    
    public static boolean productLoaded(int productID){
        return loadedProduct.getProductID() == productID;
    }
    
    public static boolean productArchived(int productID){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        return loadedProduct.isArchived();
    }
    
    public static void archiveProduct(int productID){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        loadedProduct.setArchived(true);
        saveProduct();
    }
    
    public static void setProductStock(int productID, int amnt){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        loadedProduct.setInStock(amnt);
        saveProduct();
    } 
    
    public static void setMinAge(int productID, int age){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        loadedProduct.setMinAge(age);
        saveProduct();
    }
    
    public static void setImageFolder(int productID, String folder){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        loadedProduct.setImagePath("images" + System.getProperty("file.separator") + folder);
        saveProduct();
    }
    
    public static void setImageName(int productID, String name){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        loadedProduct.setImageName(name);
        saveProduct();
    }
    
    public static int getStock(int productID){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        return loadedProduct.getInStock();
    }
    
    public static int getMinAge(int productID){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        return loadedProduct.getMinAge();
    }
    
    public static String getImagePath(int productID){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        if(imageExists(productID)){
            return loadedProduct.getImagePath();
        }
        return "images";
    }
    
    public static String getImageName(int productID){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        if(imageExists(productID)){
            return loadedProduct.getImageName();
        }
        return "noImage.jpg";
    }
    
    public static double getPrice(int productID){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        return loadedProduct.getPrice();
    }
    
    public static void setPrice(int productID, double price){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        loadedProduct.setPrice(price);
        saveProduct();
    }
    
    public static String getProductDescription(int productID){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        return loadedProduct.getDescription();
    }
    
    public static void setProductDescription(int productID, String desc){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        loadedProduct.setDescription(desc);
        saveProduct();
    }
    
    public static boolean imageExists(int productID){
        if(!productLoaded(productID)){
            loadProduct(productID);
        }
        return loadedProduct.imageExists(loadedProduct.getImagePath(), loadedProduct.getImageName());
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
    public static ArrayList readFile(String filename){
        ArrayList temp = null;
        File myfile = new File(filename);
        if(!myfile.exists()){
            try{ 
                myfile.createNewFile();
            }
            catch(IOException e){
                System.out.println("An error occurred in readFile() try block 1.");
            }
        }
        if(myfile.length() > 0){
        try{
            FileInputStream fis = new FileInputStream(myfile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            temp = (ArrayList)ois.readObject();
            ois.close();
        }
        catch(IOException | ClassNotFoundException e){
            System.out.println("An error occurred in readFile() try block 2.");
        }}
        return temp;
    }
    
    public static void saveFile(ArrayList mylist, String fileName){
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
    



