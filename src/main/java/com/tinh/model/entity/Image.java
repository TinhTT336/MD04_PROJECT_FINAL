package com.tinh.model.entity;

public class Image {
    private int imageId;
    private int productId;
    private String src;

    public Image() {
    }

    public Image(int imageId, int productId, String src) {
        this.imageId = imageId;
        this.productId = productId;
        this.src = src;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
