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
import java.time.LocalDateTime;
import java.time.LocalDate;
//import java.io.FileNotFoundException;
//import java.util.Scanner;

public class ProjectDB {
    public static void main(String[] args) {
//        long phone = 123456789L;
//        rewardsEnroll(phone);
//        double points = getBalance(phone);
//        setRewardsBalance(1234,100.3);
//        phone = 3588;
//        rewardsEnroll(phone);
//        setRewardsBalance(phone,14.3);
//        points = getBalance(phone);
//        System.out.println(points);
        createSettings("admin");
        
    }
    private static merchant loadedMerchant;
    private static int currentMerchant;
    private static product loadedProduct;
    private static int currentProduct;
    private static rewards loadedRewards;
    private static long currentRewards;
    private static settings loadedSettings;
    private static String currentSettings;
    private static transaction loadedTransaction;
    private static int currentTransaction;
    private static ArrayList<transItem> loadedItemList;
    private static int currentItemList;
//    private static transItem loadedTransItem;
//    private static int currentTransItem;
    
    
    

    //transactions
//    public static boolean transactionLoaded(int transID){
//        return loadedTransaction.getTransID() == transID;
//    }
    
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
        currentTransaction = transactionID;
        return transactionID;
    }
    
    public static void loadTransaction(int transactionID){
        ArrayList<transaction> temp = readFile("transaction.pdb");
        temp.stream().filter((t) -> (transactionID == t.getTransID())).forEachOrdered((t) -> {
            loadedTransaction = t;
            loadedItemList = t.getItemList();
        });
        currentTransaction = transactionID;
    }
    
    public static void saveTransaction(){
        ArrayList<transaction> temp = readFile("transaction.pdb");
        int index = -1;
        if(temp == null){
            temp = new ArrayList<>(1);
        }
        else{
            for(int i = 0; i < temp.size();i++){
                if(currentTransaction == temp.get(i).getTransID()){
                    index = i;
                }
            }
            if(index > 0){
                temp.remove(index);
            }
        }
        temp.add(loadedTransaction);
        saveFile(temp, "transaction.pdb");
    }
    
    public static LocalDateTime getDate(int transactionID){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
    return loadedTransaction.getTransDate();
}
    
    public static void voidTransaction(int transactionID){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        loadedTransaction.setVoided(true);
        saveTransaction();
    }
    
    public static boolean isVoided(int transactionID){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        return loadedTransaction.isVoided();
    }
    
    
    public static double getSubtotal(int transactionID){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        return loadedTransaction.getSub();
    }
    
    public static void setSubtotal(int transactionID, double sub){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        loadedTransaction.setSub(sub);
        saveTransaction();
    }
    
    public static double getTax(int transactionID){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        return loadedTransaction.getTax();
    }
    
    public static void setTax(int transactionID, double tax){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        loadedTransaction.setTax(tax);
        saveTransaction();
    }
    
    public static double getTotal(int transactionID){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        return loadedTransaction.getTotal();
    }
    
    public static void setTotal(int transactionID, double total){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        loadedTransaction.setTotal(total);
        saveTransaction();
    }
    
    public static int getPointsRedeemed(int transactionID){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        return loadedTransaction.getPointsRedeemed();
    }
    
    public static void setPointsRedeemed(int transactionID, int points){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        loadedTransaction.setPointsRedeemed(points);
        saveTransaction();
    }
    
    public static int getPointsEarned(int transactionID){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        return loadedTransaction.getPointsEarned();
    }
    
    public static void setPointsEarned(int transactionID, int points){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        loadedTransaction.setPointsEarned(points);
        saveTransaction();
    }
    
    public static String printTransaction(int transactionID){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        return loadedTransaction.toString();
    }
    
    public static String dailyTransactionReport(String date){//date format "yyyy-MM-dd", example "2019-03-29"
        LocalDate target = LocalDate.parse(date);
        String output = "";
        ArrayList<transaction> temp = readFile("transaction.pdb");
        for(transaction t : temp){
            if(target.isEqual(t.getTransDate().toLocalDate())){
                output = output + t.toString() + "\n";
            }
        }
        return output;
    }
    
    public static void addItem(int transactionID, int productID, int quant){
        int itemID;
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        
        itemID = -1;
        for (transItem i : loadedItemList){
            if(i.getTransItemID() > itemID){
                itemID = i.getTransItemID();
            }
        }
        itemID++;
        loadedTransaction.addItem(itemID, productID, quant);
        saveTransaction();
    }
    
    public static void removeItem(int transactionID, int itemID){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        loadedTransaction.removeItem(itemID);
        saveTransaction();
        loadedItemList = loadedTransaction.getItemList();
    }
    
    public static String manyDayTransactionReport(String startDate, String endDate){//date format "yyyy-MM-dd", example "2019-03-29"
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        String output = "";
        ArrayList<transaction> temp = readFile("transaction.pdb");
        for(transaction t : temp){
            LocalDate transDate = t.getTransDate().toLocalDate();
            if(!transDate.isBefore(start) && !transDate.isAfter(end)){
                output = output + t.toString() + "\n";
            }
        }
        return output;
    }
    
    public static void getTransItemList (int transactionID){
        if(currentTransaction != transactionID){
            loadTransaction(transactionID);
        }
        loadedItemList = loadedTransaction.getItemList();
        currentItemList = transactionID;
    }
    
        public int getTransItemID(transItem t){
            return t.getTransItemID();
        }
        
        public String getDescription(transItem t) {
            return t.getDescription();
        }

        public int getQuant(transItem t) {
            return t.getQuant();
        }

        public double getPrice(transItem t) {
            return t.getPrice();
        }
    



    //rewards
    public static boolean rewardsExists(ArrayList<rewards> temp, long phone){
        if(temp == null){
            return false;
        }
        return temp.stream().anyMatch((r) -> (phone == r.getPhone()));
    }
    
    public static void rewardsEnroll(long phone){
        ArrayList<rewards> temp = readFile("rewards.pdb");
        if(rewardsExists(temp, phone)){
            loadRewards(phone);
        }
        else{
            loadedRewards = new rewards(phone);
            saveRewards();
            currentRewards = phone;
        }
    }

    public static void loadRewards(long phone){
        ArrayList<rewards> temp = readFile("rewards.pdb");
        temp.stream().filter((r) -> (phone == r.getPhone())).forEachOrdered((r) -> {
            loadedRewards = r;
        });
        currentRewards = phone;
    }
    
    public static void saveRewards(){
        ArrayList<rewards> temp = readFile("rewards.pdb");
        int index = -1;
        if(temp == null){
            temp = new ArrayList<>(1);
        }
        else{
            for(int i = 0; i < temp.size(); i++){
                if(currentRewards == temp.get(i).getPhone()){
                    index = i;
                }
            }
            if(index > -1){
                temp.remove(index);
            }
        }
        temp.add(loadedRewards);
        saveFile(temp, "rewards.pdb");
    }
    
    public static void addTransaction(long phone, int transactionID){
        if(phone != currentRewards){
            loadRewards(phone);
        }
        loadedRewards.addTransaction(transactionID);
        saveRewards();
    }
    
    public static double getBalance(long phone){
        if(phone != currentRewards){
            loadRewards(phone);
        }
        return loadedRewards.getBalance();
    }

    public static String getCustName(long phone){
        if(phone != currentRewards){
            loadRewards(phone);
        }
        return loadedRewards.getCustName();
    }
    
    public static ArrayList<Integer> getTransactionHistory(long phone){
        if(phone != currentRewards){
            loadRewards(phone);
        }
        return loadedRewards.getTransactionHistory();
    }
    
    public static void removeTransaction(long phone, int transactionID){
        if(phone != currentRewards){
            loadRewards(phone);
        }
        loadedRewards.removeTransaction(transactionID);
        saveRewards();
    }
    
    public static void resetRewards(long phone){
        if(phone != currentRewards){
            loadRewards(phone);
        }
        loadedRewards.reset();
        saveRewards();
    }
    
    public static void setRewardsBalance(long phone, double balance){
        if(phone != currentRewards){
            loadRewards(phone);
        }
        loadedRewards.setBalance(balance);
        saveRewards();
    }
    
    public static void setCustName(long phone, String name){
        if(phone != currentRewards){
            loadRewards(phone);
        }
            loadedRewards.setCustName(name);
            saveRewards();
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
        currentProduct = productID;
        return productID;
    }
    
    public static void loadProduct(int productID){
        ArrayList<product> temp = readFile("product.pdb");
        temp.stream().filter((p) -> (productID == p.getProductID())).forEachOrdered((p) -> {
            loadedProduct = p;
        });
        currentProduct = productID;
    }
    
    public static void saveProduct(){
        ArrayList<product> temp = readFile("product.pdb");
        int index = -1;
        if(temp == null){
            temp = new ArrayList<>(1);
        }
        else{
            for(int i = 0; i < temp.size(); i++){
                if(currentProduct == temp.get(i).getProductID()){
                    index = i;
                }
            }
            if(index > -1){
                temp.remove(index);
            }
        }
        temp.add(loadedProduct);
        saveFile(temp, "product.pdb");
    }
    
//    public static boolean productLoaded(int productID){
//        return loadedProduct.getProductID() == productID;
//    }
//    
    public static boolean productArchived(int productID){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        return loadedProduct.isArchived();
    }
    
    public static void archiveProduct(int productID){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        loadedProduct.setArchived(true);
        saveProduct();
    }
    
    public static void setProductStock(int productID, int amnt){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        loadedProduct.setInStock(amnt);
        saveProduct();
    } 
    
    public static void setMinAge(int productID, int age){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        loadedProduct.setMinAge(age);
        saveProduct();
    }
    
    public static void setImageFolder(int productID, String folder){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        loadedProduct.setImagePath("images" + System.getProperty("file.separator") + folder);
        saveProduct();
    }
    
    public static void setImageName(int productID, String name){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        loadedProduct.setImageName(name);
        saveProduct();
    }
    
    public static int getStock(int productID){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        return loadedProduct.getInStock();
    }
    
    public static int getMinAge(int productID){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        return loadedProduct.getMinAge();
    }
    
    public static String getImagePath(int productID){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        if(imageExists(productID)){
            return loadedProduct.getImagePath();
        }
        return "images";
    }
    
    public static String getImageName(int productID){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        if(imageExists(productID)){
            return loadedProduct.getImageName();
        }
        return "noImage.jpg";
    }
    
    public static double getPrice(int productID){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        return loadedProduct.getPrice();
    }
    
    public static void setPrice(int productID, double price){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        loadedProduct.setPrice(price);
        saveProduct();
    }
    
    public static String getProductDescription(int productID){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        return loadedProduct.getDescription();
    }
    
    public static void setProductDescription(int productID, String desc){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        loadedProduct.setDescription(desc);
        saveProduct();
    }
    
    public static boolean imageExists(int productID){
        if(currentProduct != productID){
            loadProduct(productID);
        }
        return loadedProduct.imageExists();
    }

    
    

    //merchants
    public static int createMerchant(){
        int merchantID;
        String fileName = "merchant.pdb";
        ArrayList<merchant> mylist = readFile(fileName);
        merchantID = -1;
        if(mylist != null){
            for (merchant m : mylist){
                if(m.getMerchantID() > merchantID){
                    merchantID = m.getMerchantID();
                }
            }
        }
        merchantID++;
        loadedMerchant = new merchant(merchantID);
        saveMerchant();
        currentMerchant = merchantID;
        return merchantID;
    }
    
    public static void loadMerchant(int merchantID){
        ArrayList<merchant> temp = readFile("merchant.pdb");
        temp.stream().filter((m) -> (merchantID == m.getMerchantID())).forEachOrdered((m) -> {
            loadedMerchant = m;
        });
        currentMerchant = merchantID;
    }
    
    public static void saveMerchant(){
        ArrayList<merchant> temp = readFile("merchant.pdb");
        int index = -1;
        if(temp == null){
            temp = new ArrayList<>(1);
        }
        else{
            for(int i = 0; i < temp.size(); i++){
                if(currentMerchant == temp.get(i).getMerchantID()){
                    index = i;
                }
            }
            if(index > -1){
                temp.remove(index);
            }
        }
        temp.add(loadedMerchant);
        saveFile(temp, "merchant.pdb");
    }
    
//    public static boolean merchantLoaded(int merchantID){
//        return loadedMerchant.getMerchantID() == merchantID;
//    }
//
    public static String getMerchantPassword(int merchantID){
        if(merchantID != currentMerchant){
            loadMerchant(merchantID);
        }
        return loadedMerchant.getPassword();
    }
    
    public static void setMerchantPassword(int merchantID, String password){
        if(merchantID != currentMerchant){
            loadMerchant(merchantID);
        }
        loadedMerchant.setPassword(password);
        saveMerchant();
    }
    
    public static boolean isAdmin(int merchantID){
        if(merchantID != currentMerchant){
            loadMerchant(merchantID);
        }
        return loadedMerchant.isAdmin();
    }
    
    public static void setAdmin(int merchantID, boolean status){
        if(merchantID != currentMerchant){
            loadMerchant(merchantID);
        }
        loadedMerchant.setAdmin(status);
        saveMerchant();
    }
    
    public static String getMerchantName(int merchantID){
        if(merchantID != currentMerchant){
            loadMerchant(merchantID);
        }
        return loadedMerchant.getMerchantName();
    }
    
    public static void setMerchantName(int merchantID, String name){
        if(merchantID != currentMerchant){
            loadMerchant(merchantID);
        }
        loadedMerchant.setMerchantName(name);
        saveMerchant();
    }
    
    
    


    //settings
//    public boolean settingsExists(String profileName){
//        ArrayList<settings> temp = readFile("settings.pdb");
//        return temp.stream().anyMatch((s) -> (profileName.equals(s.getProfileName())));
//    }
//    
    public static boolean settingsExists(ArrayList<settings> temp, String profileName){
        if (temp == null){
            return false;
        }
        return temp.stream().anyMatch((s) -> (profileName.equals(s.getProfileName())));
    }
    
    public static void createSettings(String profileName){
        ArrayList<settings> temp = readFile("settings.pdb");
        if(settingsExists(temp, profileName)){
            loadSettings(profileName);
        }
        else{
            loadedSettings = new settings(profileName);
            System.out.println("Profile name: " + loadedSettings.getProfileName());
            saveSettings();
        }
        currentSettings = profileName;
    }
    
    public static void loadSettings(String profileName){
        ArrayList<settings> temp = readFile("settings.pdb");
        temp.stream().filter((s) -> (profileName.equals(s.getProfileName()))).forEachOrdered((s) -> {
            loadedSettings = s;
        });
        currentSettings = profileName;
    }
    
    public static void saveSettings(){
        ArrayList<settings> temp = readFile("settings.pdb");
        int index = -1;
        if(temp == null){
            temp = new ArrayList<>(1);
        }
        else{
                for(int i = 0; i < temp.size();i++){
                        if(currentSettings.equals(temp.get(i).getProfileName())){
                                index = i;
                        }
                }
                if(index > 0){
                        temp.remove(index);
                }
        }
        temp.add(loadedSettings);
        saveFile(temp, "settings.pdb");
    }

    public static void setPointsPerDollar(String profileName, double PointsPerDollar){
        if(!currentSettings.equals(profileName)){
            loadSettings(profileName);
        }
        loadedSettings.setPointsPerDollar(PointsPerDollar);
        saveSettings();
    }
    
    public static double getPointsPerDollar(String profileName){
        if(!currentSettings.equals(profileName)){
            loadSettings(profileName);
        }
        return loadedSettings.getPointsPerDollar();
    }
    
    public static void setDollarsPerPoint(String profileName, double dollarsPerPoint){
        if(!currentSettings.equals(profileName)){
            loadSettings(profileName);
        }
            loadedSettings.setDollarsPerPoint(dollarsPerPoint);
            saveSettings();
    }
    
    public static double getDollarsPerPoint(String profileName){
        if(!currentSettings.equals(profileName)){
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
                System.out.println(e);
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
            System.out.println(e);
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
                System.out.println("An error occurred in saveFile.");
            }
    }
    
    

    
    


}
    



