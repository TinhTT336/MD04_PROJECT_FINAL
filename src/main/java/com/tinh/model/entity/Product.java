package com.tinh.model.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class Product {
    private  int productId;
    @NotEmpty(message = "ProductName must not be empty!")
    private String productName;
    @Positive(message = "Price must be greater than 0!")
    private double price;
    @NotEmpty(message = "Description must not be empty!")
    private String description;
    private String brand;
    @PositiveOrZero(message = "Stock must be greater than or is 0!")
    private int stock;
    private boolean productStatus=true;
    @NotNull(message = "Image must not be null!")
    private String image;
    private Category category;

    public Product() {
    }

    public Product(int productId, String productName, double price, String description, String brand, int stock, boolean productStatus, String image, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.stock = stock;
        this.productStatus = productStatus;
        this.image = image;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
