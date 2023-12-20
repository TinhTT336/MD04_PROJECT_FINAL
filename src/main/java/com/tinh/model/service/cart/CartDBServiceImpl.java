package com.tinh.model.service.cart;

import com.tinh.model.dao.cart.CartDao;
import com.tinh.model.entity.CartItemDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartDBServiceImpl implements CartDBService {
    @Autowired
    private CartDao cartDao;

    @Override
    public Integer checkCartUser(Integer userId) {
        return cartDao.checkCartUser(userId);
    }

    @Override
    public Integer save(Integer userId) {
        return cartDao.save(userId);
    }




}
