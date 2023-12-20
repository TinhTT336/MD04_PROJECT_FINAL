package com.tinh.model.dao.order;

import com.tinh.model.entity.CartItemDB;
import com.tinh.model.entity.Order;
import com.tinh.model.entity.OrderDetail;
import com.tinh.model.entity.Product;

import java.util.List;

public interface OrderDao {
    List<Order> findAll();

    Order save(Order order, List<CartItemDB>list);

    void changeStatus(Integer status,Integer orderId);
    Order findByOrderId(Integer orderId);
    List<Order> findByUserId(Integer userId);
    List<OrderDetail>findOrderDetailByOrderId(Integer orderId);
    List<Order> pagination(Integer limit, Integer currentPage);
    Integer totalPage(Integer limit, Integer currentPage);
    List<Order> search(String name,int limit,int currentPage);
    Integer totalPageSearch(String name,int limit);
    Integer totalOrderSearch(String name);
    Integer countOrderByStatus(Integer status);
}
