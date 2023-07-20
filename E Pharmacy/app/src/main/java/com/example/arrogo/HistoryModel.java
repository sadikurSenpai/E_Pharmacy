package com.example.arrogo;

public class HistoryModel {
   private String productName,productPrice,pictureLink;

    public HistoryModel(String productName, String productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;

    }

    public HistoryModel() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
