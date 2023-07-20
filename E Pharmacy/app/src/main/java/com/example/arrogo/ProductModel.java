package com.example.arrogo;

public class ProductModel {
    private String productImage,productName,companyName,sellingPrice,MRP,description,title;

    public ProductModel(String productImage, String productName, String companyName, String sellingPrice, String MRP, String description, String title) {
        this.productImage = productImage;
        this.productName = productName;
        this.companyName = companyName;
        this.sellingPrice = sellingPrice;
        this.MRP = MRP;
        this.description = description;
        this.title = title;
    }

    public ProductModel() {
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
