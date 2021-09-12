package com.teesofttech.mkremit.models;

import java.io.Serializable;

public class WalletModel implements Serializable {
    /* "id": 8,
            "dateCreated": "2020-07-08T00:59:39.5832958",
            "walletType": "Commission",
            "balance": 0.00,
            "owner": null,
            "walletName": "Commission",
            "walletDescription": null,
            "commissionMode": 0,
            "mode": "Base"*/

    private String id;
    private String dateCreated;
    private String walletType;
    private String balance;
    private String walletName;
    private String commissionMode;
    private String mode;
    private String walletDescription;
    private String vendorLogo;


    public String getVendorLogo() {
        return vendorLogo;
    }

    public void setVendorLogo(String vendorLogo) {
        this.vendorLogo = vendorLogo;
    }

    public String getWalletDescription() {
        return walletDescription;
    }

    public void setWalletDescription(String walletDescription) {
        this.walletDescription = walletDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getWalletType() {
        return walletType;
    }

    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getCommissionMode() {
        return commissionMode;
    }

    public void setCommissionMode(String commissionMode) {
        this.commissionMode = commissionMode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
