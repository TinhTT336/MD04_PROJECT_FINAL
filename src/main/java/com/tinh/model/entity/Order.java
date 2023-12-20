package com.tinh.model.entity;

import com.tinh.model.dto.user.UserCheckoutDTO;

import java.sql.Date;

public class Order {
    private int orderId;
    private User user;
    private double orderTotal;
    private int orderStatus = 0;
    private Date order_at;
    private String address;
    private String note;

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    private String payment;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrder_at() {
        return order_at;
    }

    public void setOrder_at(Date order_at) {
        this.order_at = order_at;
    }
}
