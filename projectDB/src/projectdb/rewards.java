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
import java.util.ArrayList;
import java.util.Objects;

public class rewards implements Serializable{
    int phone;
    double balance;
    String custName;
    ArrayList<Integer> transactionHistory;

    public rewards(int phone) {
        this.phone = phone;
        transactionHistory = new ArrayList<Integer>();
    }
    
    public void reset(){
        balance = 0.0;
        transactionHistory = new ArrayList<Integer>();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public ArrayList<Integer> getTransactionHistory() {
        return transactionHistory;
    }

    public int getPhone() {
        return phone;
    }
    
    public void addTransaction(int transactionID){
        transactionHistory.add((Integer)transactionID);
    }
    
    public void removeTransaction(Integer transactionID){
        int index = -1;
        for(int i = 0; i < transactionHistory.size();i++){
            if(Objects.equals(transactionID, transactionHistory.get(i))){
                index = i;
            }
        }
        if (index > -1){
            transactionHistory.remove(index);  
        }
    }
}
