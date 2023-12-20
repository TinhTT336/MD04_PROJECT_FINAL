package com.tinh.model.entity;

import com.tinh.model.dto.user.UserLoginDTO;

public class Cart {
    private int cartId;
    private UserLoginDTO user;

    public Cart() {
    }

    public Cart(int cartId, UserLoginDTO user) {
        this.cartId = cartId;
        this.user = user;
    }
}
