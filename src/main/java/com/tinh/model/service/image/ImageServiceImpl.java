package com.tinh.model.service.image;

import com.tinh.model.dao.image.ImageDao;
import com.tinh.model.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageDao imagesDao;

    @Override
    public List<Image> findAll() {
        return imagesDao.findAll();
    }

    @Override
    public List<Image> findByProductId(int productId) {
        return imagesDao.findByProductId(productId);
    }

    @Override
    public boolean saveOrUpdate(Image images) {
        return imagesDao.saveOrUpdate(images);
    }

    @Override
    public void delete(Integer productId) {
        imagesDao.delete(productId);
    }
}
