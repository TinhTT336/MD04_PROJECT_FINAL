package com.tinh.controller.user;

import com.tinh.model.dto.user.UserLoginDTO;
import com.tinh.model.entity.Product;
import com.tinh.model.entity.Wishlist;
import com.tinh.model.service.cart.WishService;
import com.tinh.model.service.product.ProductService;
import com.tinh.model.service.wishlist.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private ProductService productService;

    @GetMapping("/wishlist")
    public String wishlist(Model model) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        if (userLoginDTO != null) {
            List<Wishlist> wishlist = wishlistService.findByUserId(userLoginDTO.getUserId());
            model.addAttribute("wishlist", wishlist);
        } else {
            return "redirect:/signin?action=wishlist";
        }

        return "client/wishlist";
    }

    @GetMapping("/wishlist-delete/{id}")
    public String deleteWishlist(@PathVariable("id") Integer productId) {
        wishlistService.delete(productId);
        return "redirect:/wishlist";
    }

    @PostMapping("/add-wishlist")
    public String addToWish(@RequestParam("productId") Integer productId) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) httpSession.getAttribute("userLoginUser");

        if (userLoginDTO != null) {
            Wishlist wishlist = new Wishlist();
            wishlist.setUserId(userLoginDTO.getUserId());
            wishlist.setProduct(productService.findById(productId));

            Wishlist wish = wishlistService.findByProductId(productId, userLoginDTO.getUserId());
            if (wish == null || wish.getWishlistId() == 0) {
                wishlistService.save(wishlist);
            }
        }
        return "redirect:/wishlist";
    }
}
