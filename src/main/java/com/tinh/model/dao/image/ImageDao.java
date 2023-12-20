package com.tinh.model.dao.image;

import com.tinh.model.entity.Image;

import java.util.List;

public interface ImageDao {
    List<Image> findAll();
    List<Image>findByProductId(int productId);
    boolean saveOrUpdate(Image image);
    void delete(Integer productId);
}
