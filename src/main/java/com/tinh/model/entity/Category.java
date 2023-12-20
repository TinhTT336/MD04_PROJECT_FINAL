package com.tinh.model.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Category {
    private int categoryId;
    @NotEmpty(message = "CategoryName must not be empty!")
    private String categoryName;
    private boolean categoryStatus;
    @NotNull(message = "ParentId must not be empty!")
    private int parentId;

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category() {
    }

    public Category(int categoryId, String categoryName, boolean categoryStatus, int parentId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryStatus = categoryStatus;
        this.parentId = parentId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(boolean categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
