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
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class transaction implements Serializable{
    private final int transID;
    private final LocalDateTime transTime;
    private ArrayList<transItem> itemList;
    private double sub;
    private double tax;
    private double total;
    private int pointsRedeemed;
    private int pointsEarned;
    private boolean voided;

    public transaction(int transID) {
        this.transID = transID;
        transTime = LocalDateTime.now();
        voided = false;
    }

    public boolean isVoided() {
        return voided;
    }

    public void setVoided(boolean voided) {
        this.voided = voided;
    }

    public int getTransID() {
        return transID;
    }

    public LocalDateTime getTransDate() {
        return transTime;
    }

    public double getSub() {
        return sub;
    }

    public void setSub(double sub) {
        this.sub = sub;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getPointsRedeemed() {
        return pointsRedeemed;
    }

    public void setPointsRedeemed(int pointsRedeemed) {
        this.pointsRedeemed = pointsRedeemed;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }
    
    public void addItem(int transItemID, int productID, int quant){
        itemList.add(new transItem(transItemID, productID, quant));
    }
    
    public void removeItem(int itemID){
        int index = -1;
        for(int i = 0; i < itemList.size();i++){
            if(i == itemID){
                index = i;
            }
        }
        if(index > -1){
            itemList.remove(index);
        }
    }

    public ArrayList<transItem> getItemList() {
        return itemList;
    }
    
//    private class transItem{
//        private final int transItemID;
//        private final int productID;
//        private final String description;
//        private final int quant;
//        private final double price;
//
//        public transItem(int transItemID, int productID, int quant) {
//            this.transItemID = transItemID;
//            this.productID = productID;
//            this.quant = quant;
//            description = ProjectDB.getProductDescription(productID);
//            price = ProjectDB.getPrice(productID);
//        }
//            
//        public int getTransItemID(){
//            return transItemID;
//        }
//        
//        public String getDescription() {
//            return description;
//        }
//
//        public int getQuant() {
//            return quant;
//        }
//
//        public double getPrice() {
//            return price;
//        }
//    }

    @Override
    public String toString() {
        String output = "Transaction ID:\t" + transID + "\t" + transTime + "\n";
        for(transItem t : itemList){
            output = output + t.toString() + "\n";
        }
        if(isVoided()){
            output = output + "VOIDED!!!\n";
        }
        output = output + "Subtotal:\t" + sub + "\n";
        output = output + "Tax:\t" + tax + "\n";
        output = output + "Total:\t" + total + "\n";
        output = output + "Points Redeemed:\t" + pointsRedeemed + "\n";
        output = output + "Points Earned:\t" + pointsEarned + "\n";
        return output;
    }
}
