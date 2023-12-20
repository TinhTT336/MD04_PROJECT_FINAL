//package com.tinh.controller.user;
//
//import com.tinh.model.entity.CartItem;
//import com.tinh.model.entity.Product;
//import com.tinh.model.service.cart.CartService;
//import com.tinh.model.service.product.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//public class CartController {
//    @Autowired
//    private CartService cartService;
//    @Autowired
//    private ProductService productService;
//
//    @GetMapping("/cart")
//    public String cart(Model model) {
//        List<CartItem> cartItems = cartService.getCartItems();
//        model.addAttribute("carts", cartItems);
//
//        double cartTotal = cartService.getCartTotal(cartItems);
//        model.addAttribute("cartTotal", cartTotal);
//        return "client/cart";
//    }
//
//    @PostMapping("/addCart")
//    public String addCart(@RequestParam("quantity") Integer quantity, @RequestParam("productId") Integer id) {
//        CartItem cartItem = new CartItem();
//        Product product = productService.findById(id);
//        cartItem.setProduct(product);
//        cartItem.setQuantity(quantity);
//        cartService.addToCart(cartItem);
//        return "redirect:/cart";
//    }
//
//    @PostMapping("/add-cart-quick")
//    public String addCartQuick(@RequestParam("productId") Integer id){
//        CartItem cartItem = new CartItem();
//        Product product = productService.findById(id);
//        cartItem.setProduct(product);
//        cartItem.setQuantity(1);
//        cartService.addToCartQuick(cartItem);
//        return "redirect:/product";
//    }
//
//    @GetMapping("/cart-delete/{id}")
//    public String deleteCartItem(@PathVariable("id") Integer id) {
//        cartService.delete(id);
//        return "redirect:/cart";
//    }
//    @GetMapping("/cart-increase/{id}")
//    public String increase( @PathVariable("id") Integer id){
//        cartService.increase( id);
//        return "redirect:/cart";
//    }
//
//    @GetMapping("/cart-decrease/{id}")
//    public String decrease (@PathVariable("id") Integer id){
//        cartService.decrease( id);
//        return "redirect:/cart";
//    }
//    @GetMapping("/checkout")
//    public String checkout(Model model){
//        List<CartItem> cartItems = cartService.getCartItems();
//        model.addAttribute("carts", cartItems);
//
//        double cartTotal = cartService.getCartTotal(cartItems);
//        model.addAttribute("cartTotal", cartTotal);
//        return "client/checkout";
//    }
//
//}
