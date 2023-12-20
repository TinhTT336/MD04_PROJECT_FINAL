package com.tinh.model.service.image;

import com.tinh.model.entity.Image;

import java.util.List;

public interface ImageService {
    List<Image> findAll();
    List<Image>findByProductId(int productId);
    boolean saveOrUpdate(Image images);
    void delete(Integer productId);
}
