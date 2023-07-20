package com.example.arrogo;

public class MyServiceModel {
    private String randomKey,productName,productPrice,productTitle,mail;

    public MyServiceModel(String randomKey, String productName, String productPrice, String productTitle,String m) {
        this.randomKey = randomKey;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productTitle = productTitle;
        this.mail=m;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public MyServiceModel() {
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
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

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
}
