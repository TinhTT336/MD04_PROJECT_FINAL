package com.tinh.model.dao.wishlist;

import com.tinh.model.entity.Wishlist;

import java.util.List;

public interface WishlistDao {
    List<Wishlist> findByUserId(Integer userId);
    void save(Wishlist wishlist);
    void delete(Integer productId);
    Wishlist findByProductId(Integer product,Integer userId);
}
