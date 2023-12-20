package com.tinh.model.dao.cart;

public interface CartDao {
    Integer checkCartUser(Integer userId);
    Integer save (Integer userId);
}
