/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdb;

import java.io.Serializable;

/**
 *
 * @author anazo
 */
//import java.util.ArrayList;

public class product implements Serializable{
    int productID;
    double price;
    String description;

    public product(int productID) {
        this.productID = productID;
    }

    public int getProductID() {
        return productID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
