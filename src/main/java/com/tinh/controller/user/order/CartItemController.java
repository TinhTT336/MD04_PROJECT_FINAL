package com.tinh.controller.user.order;

import com.tinh.model.dto.user.UserLoginDTO;
import com.tinh.model.entity.*;
import com.tinh.model.service.cart.CartDBService;
import com.tinh.model.service.cart.CartItemDBService;
import com.tinh.model.service.cart.CartService;
import com.tinh.model.service.product.ProductService;
import com.tinh.model.service.wishlist.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartItemController {
    @Autowired
    private CartDBService cartDBService;
    @Autowired
    private CartItemDBService cartItemDBService;
    @Autowired
    private ProductService productService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/cart")
    public String cart(Model model) {
        UserLoginDTO userLogin = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        if (userLogin != null) {
            List<CartItemDB> cartItems = cartItemDBService.getCartUserLogin(cartItemDBService.getCartID(userLogin.getUserId()));
            model.addAttribute("carts", cartItems);

            int cartTotal = cartItemDBService.getCartTotal(cartItems);
            model.addAttribute("cartTotal", cartTotal);

        } else {
            return "redirect:/signin?action=cart";
        }
        return "client/cart";
    }

    @PostMapping("/addCart")
    public String addCart(@RequestParam("quantity") Integer quantity,
                          @RequestParam("productId") Integer id,
                          RedirectAttributes redirectAttributes) {
        UserLoginDTO userLogin = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        if (userLogin == null) {

            return "redirect:/signin?action=product";
        } else {

            CartItemDB cartItemDB = new CartItemDB();
            Product product = productService.findById(id);

            cartItemDB.setCartId(cartItemDBService.getCartID(userLogin.getUserId()));
            cartItemDB.setProduct(product);
            cartItemDB.setQuantity(quantity);
            if (!cartItemDBService.addToCart(cartItemDB, cartItemDB.getCartId())) {
                redirectAttributes.addFlashAttribute("errQuantity", "Quantity must not be greater than stock!");
            }
        }
        return "redirect:/cart";
    }

    @PostMapping("/add-cart-quick")
    public String addCartQuick(@RequestParam("productId") Integer productId) {
        UserLoginDTO userLogin = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        if (userLogin == null) {
            return "redirect:/signin?action=product";
        } else {
            CartItemDB cartItemDB = new CartItemDB();
            Product product = productService.findById(productId);

            cartItemDB.setCartId(cartItemDBService.getCartID(userLogin.getUserId()));
            cartItemDB.setProduct(product);
            cartItemDB.setQuantity(1);
            cartItemDBService.addToCart(cartItemDB, cartItemDB.getCartId());
        }
        return "redirect:/product";
    }

    @PostMapping("/add-cart")
    public String addCartFromWishlist(@RequestParam("productId") Integer productId) {
        UserLoginDTO userLogin = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        if (userLogin == null) {
            return "redirect:/signin?action=product";
        } else {
            CartItemDB cartItemDB = new CartItemDB();
            Product product = productService.findById(productId);

            cartItemDB.setCartId(cartItemDBService.getCartID(userLogin.getUserId()));
            cartItemDB.setProduct(product);
            cartItemDB.setQuantity(1);
            cartItemDBService.addToCart(cartItemDB, cartItemDB.getCartId());
            wishlistService.delete(productId);
        }
        return "redirect:/wishlist";
    }

    @GetMapping("/cart-delete/{id}")
    public String deleteCartItem(@PathVariable("id") Integer productId) {
        UserLoginDTO userLogin = (UserLoginDTO) httpSession.getAttribute("userLoginUser");

        cartItemDBService.delete(productId, cartItemDBService.getCartID(userLogin.getUserId()));
        return "redirect:/cart";
    }

    @GetMapping("/cart-delete")
    public String deleteAllCartItem() {
        UserLoginDTO userLogin = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        int cartId = cartItemDBService.getCartID(userLogin.getUserId());
        cartItemDBService.deleteCartItem(cartId);
        return "redirect:/cart";
    }

    @GetMapping("/cart-increase/{id}")
    public String increase(@PathVariable("id") Integer productId, RedirectAttributes redirectAttributes) {
        UserLoginDTO userLogin = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        int stock = productService.findById(productId).getStock();
        if (cartItemDBService.increase(productId, cartItemDBService.getCartID(userLogin.getUserId()))) {
            redirectAttributes.addFlashAttribute("errQuantity", "Quantity must not be greater than stock!");
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart-decrease/{id}")
    public String decrease(@PathVariable("id") Integer productId) {
        UserLoginDTO userLogin = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        cartItemDBService.decrease(productId, cartItemDBService.getCartID(userLogin.getUserId()));
        return "redirect:/cart";
    }

}
