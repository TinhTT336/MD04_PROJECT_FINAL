package com.tinh.model.service.cart;

import com.tinh.model.entity.CartItemDB;

import java.util.List;

public interface CartDBService {
    Integer checkCartUser(Integer userId);

    Integer save(Integer userId);

}
