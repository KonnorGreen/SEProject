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
    int transID;
    LocalDateTime transTime;
    ArrayList<transItem> itemList;
    double sub;
    double tax;
    double total;
    int pointsRedeemed;
    int pointsEarned;
    boolean voided;

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
    
    private class transItem{
        int transItemID;
        int productID;
        String description;
        int quant;
        double price;

        public transItem(int transItemID, int productID, int quant) {
            this.transItemID = transItemID;
            this.productID = productID;
            this.quant = quant;
            description = ProjectDB.getDescription(productID);
            price = ProjectDB.getPrice(productID);
        }
            
        public int getTransItemID(){
            return transItemID;
        }

        
    
    }
    
    
}
