package com.tinh.model.service.wishlist;

import com.mysql.cj.jdbc.CallableStatement;
import com.tinh.model.dao.product.ProductDao;
import com.tinh.model.dao.wishlist.WishlistDao;
import com.tinh.model.entity.Product;
import com.tinh.model.entity.Wishlist;
import com.tinh.util.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    private WishlistDao wishlistDao;
    @Override
    public List<Wishlist> findByUserId(Integer userId) {
        return wishlistDao.findByUserId(userId);
    }

    @Override
    public void save(Wishlist wishlist) {
        wishlistDao.save(wishlist);
    }

    @Override
    public void delete(Integer productId) {
        wishlistDao.delete(productId);
    }

    @Override
    public Wishlist findByProductId(Integer productId, Integer userId) {
        return wishlistDao.findByProductId(productId, userId);
    }
}
