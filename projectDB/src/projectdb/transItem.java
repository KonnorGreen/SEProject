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
public class transItem {
        private final int transItemID;
        private final int productID;
        private final String description;
        private final int quant;
        private final double price;

        transItem(int transItemID, int productID, int quant) {
            this.transItemID = transItemID;
            this.productID = productID;
            this.quant = quant;
            description = ProjectDB.getProductDescription(productID);
            price = ProjectDB.getPrice(productID);
        }
            
        public int getTransItemID(){
            return transItemID;
        }
        
        public String getDescription() {
            return description;
        }

        public int getQuant() {
            return quant;
        }

        public double getPrice() {
            return price;
        }

    @Override
    public String toString() {
        return transItemID + "\t" + productID + "\t" + description + "\t" + quant + "\t" + price;
    }
}
