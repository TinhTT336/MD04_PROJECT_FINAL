package com.tinh.model.dto.product;

import com.tinh.model.entity.Category;
import com.tinh.model.validation.FileNotNull;
import com.tinh.model.validation.ValidImage;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class ProductDTO {
    private  int productId;
    @NotEmpty(message = "Product Name must not be empty!")
    private String productName;
//    @NotNull(message = "Price must not be empty!")
    @Positive(message = "Price must be greater than 0!")
    private double price;
    @NotEmpty(message = "Description must not be empty!")
    private String description;
    private String brand;
    private boolean productStatus=true;
//    @NotNull(message = "Product Name must not be empty!")
    @PositiveOrZero(message = "Stock must be greater than or is 0!")
    private int stock;
    @FileNotNull(message = "Image must not be null!")
//    @ValidImage(type = {"image/jpeg","image/png","image/jpg"},message = "Incorrect image format!")
    private MultipartFile image;
    private Category category;

    public ProductDTO() {
    }

    public ProductDTO(int productId, String productName, double price, String description, String brand, boolean productStatus, int stock, MultipartFile image, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.productStatus = productStatus;
        this.stock = stock;
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


    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }
}
