/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectdb;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author anazo
 */
//import java.util.ArrayList;

public class product implements Serializable{
    private final int productID;
    private double price;
    private String description;
    private int inStock;
    private int minAge;
    private String imagePath;
    private String imageName;
    private boolean archived;

    public product(int productID) {
        this.productID = productID;
        inStock = 0;
        minAge = 0;
        price = 0.0;
        description = "No description entered";
        imagePath = "/images";
        imageName = "noImage.jpg";
        archived = false;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getInStock() {
        return inStock;
    }

    public int getMinAge() {
        return minAge;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImageName() {
        return imageName;
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
    
    public boolean imageExists(){
        File myfile = new File(imagePath, imageName);
        return myfile.exists();
    }
    
    
}
