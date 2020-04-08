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

public class merchant implements Serializable{
    private int merchantID;
    private String password;
    private boolean admin;
    private String merchantName;

    public merchant(int merchantID) {
        this.merchantID = merchantID;
    }

    public int getMerchantID() {
        return merchantID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    
}
