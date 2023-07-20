package com.example.arrogo;

public class CartModel {
  private String randomKey,pictureLink,productName,productPrice,productQuantity,singleProductPrice;

    public CartModel(String randomKey,String pictureLink, String productName, String productPrice, String productQuantity, String singleProductPrice) {
        this.randomKey=randomKey;
        this.pictureLink = pictureLink;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.singleProductPrice = singleProductPrice;
    }

    public CartModel() {
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
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

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getSingleProductPrice() {
        return singleProductPrice;
    }

    public void setSingleProductPrice(String singleProductPrice) {
        this.singleProductPrice = singleProductPrice;
    }
}
