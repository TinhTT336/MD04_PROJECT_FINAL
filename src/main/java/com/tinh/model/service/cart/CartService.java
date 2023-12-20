package com.tinh.model.service.cart;

import com.tinh.model.entity.CartItem;
import com.tinh.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    List<CartItem> cartItems = new ArrayList<>();
    @Autowired
    HttpSession httpSession;

    public List<CartItem> getCartItems() {
        //kiem tra xem trong session co khong, co thi lay va gan vao cartItem/ khong co thi cho bang rong
        cartItems = ((httpSession.getAttribute("carts") != null) ? (List<CartItem>) httpSession.getAttribute("carts") : new ArrayList<>());
        return cartItems;
    }

    //chuyen the a (add to cart) thanh button type=submit boc the form ben ngoai
    public void addToCart(CartItem cartItem) {
        CartItem oldCartItem = findCartItemByProduct(cartItem.getProduct());
        if (oldCartItem != null) {
            oldCartItem.setQuantity(oldCartItem.getQuantity() + cartItem.getQuantity());
        } else {
//        day item vao cart
            cartItems.add(cartItem);
        }
//luu vao session
        httpSession.setAttribute("carts", cartItems);
    }
    public void addToCartQuick(CartItem cartItem) {
        CartItem oldCartItem = findCartItemByProduct(cartItem.getProduct());
        if (oldCartItem != null) {
            oldCartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
//        day item vao cart
            cartItems.add(cartItem);
        }
//luu vao session
        httpSession.setAttribute("carts", cartItems);
    }


    public void increase(Integer productId) {
//        CartItem cartItem = (CartItem) cartItems.stream().filter(cart -> cart.getProduct().getProductId() == productId);
        CartItem cartItem = findCartItemByProductId(productId);
        cartItem.setQuantity(cartItem.getQuantity() + 1);

        //luu vao session
        httpSession.setAttribute("carts", cartItems);
    }

    public void decrease(Integer productId) {
//        CartItem cartItem = (CartItem) cartItems.stream().filter(cart -> cart.getProduct().getProductId() == productId);
        CartItem cartItem = findCartItemByProductId(productId);
        cartItem.setQuantity(cartItem.getQuantity() - 1);
        if (cartItem.getQuantity() == 0) {
            cartItems.remove(cartItem);
        }
        //luu vao session
        httpSession.setAttribute("carts", cartItems);
    }

    public void delete(Integer productId) {
        cartItems.removeIf(cartItem -> cartItem.getProduct().getProductId() == productId);
        //luu vao session
        httpSession.setAttribute("carts", cartItems);
    }

    public CartItem findCartItemByProduct(Product product) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId() == product.getProductId()) {
                return cartItem;
            }
        }
        return null;
    }

    public Double getCartTotal(List<CartItem> cartItems) {
        double cartTotal = 0;
//        for (CartItem cartItem : cartItems) {
//            cartTotal+=cartItem.getQuantity()*cartItem.getProduct().getPrice();
//        }
        cartTotal = cartItems.stream().mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice()).sum();
        return cartTotal;
    }

    public CartItem findCartItemByProductId(Integer id) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId() == id) {
                return cartItem;
            }
        }
        return null;
    }

}
