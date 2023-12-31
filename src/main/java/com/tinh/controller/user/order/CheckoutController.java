package com.tinh.controller.user.order;

import com.tinh.model.dto.user.UserCheckoutDTO;
import com.tinh.model.dto.user.UserLoginDTO;
import com.tinh.model.entity.CartItemDB;
import com.tinh.model.entity.Order;
import com.tinh.model.entity.Product;
import com.tinh.model.entity.User;
import com.tinh.model.service.cart.CartItemDBService;
import com.tinh.model.service.order.OrderService;
import com.tinh.model.service.product.ProductService;
import com.tinh.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CheckoutController {
    @Autowired
    HttpSession httpSession;
    @Autowired
    private CartItemDBService cartItemDBService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        if (userLoginDTO == null) {
            return "redirect:/signin?action=checkout";
        }
        //tim user dua tren userLoginDTO
        User userLogin = userService.findById(userLoginDTO.getUserId());

        //tao 1 userCheckoutDTO va cast tu gia tri user tim duoc phia tren
        UserCheckoutDTO userCheckoutDTO = new UserCheckoutDTO();
        userCheckoutDTO.setUserId(userLogin.getUserId());
        userCheckoutDTO.setUsername(userLogin.getUsername());
        userCheckoutDTO.setAddress(userLogin.getAddress());
        userCheckoutDTO.setPhone(userLogin.getPhone());
        userCheckoutDTO.setEmail(userLogin.getEmail());
        userCheckoutDTO.setNote("");

        //lay cac gia tri listCart, cartTotal, userCheckoutDTO va chuyen sang view
        List<CartItemDB> list = cartItemDBService.getCartUserLogin(cartItemDBService.getCartID(userLogin.getUserId()));
        int cartTotal = cartItemDBService.getCartTotal(list);

        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("user", userCheckoutDTO);
        model.addAttribute("carts", list);
        return "client/checkout";
    }

    @PostMapping("/checkout")
    public String postCheckout(@Valid @ModelAttribute("user") UserCheckoutDTO userCheckoutDTO,
                               BindingResult result,@RequestParam("payment")String payment,
                               RedirectAttributes redirectAttributes) {
        User user = userService.findByMail(userCheckoutDTO.getEmail());
        List<CartItemDB> list = cartItemDBService.getCartUserLogin(cartItemDBService.getCartID(user.getUserId()));

        Order order = new Order();
        order.setOrderTotal(cartItemDBService.getCartTotal(list));
        order.setNote(userCheckoutDTO.getNote());
        order.setUser(user);
        order.setAddress(userCheckoutDTO.getAddress());
        order.setOrder_at(Date.valueOf(LocalDate.now()));
        order.setPayment(payment);

        for (CartItemDB cartItemDB : list) {
            for (Product product : productService.findAll()) {
                if (cartItemDB.getProduct().getProductId() == product.getProductId()) {
                    if (cartItemDB.getQuantity() > product.getStock()) {
                        redirectAttributes.addFlashAttribute("errQuantity", "Quantity must not be greater than stock!");
                        return "redirect:/cart";
                    } else {
                        product.setStock(product.getStock() - cartItemDB.getQuantity());
                        productService.update(product);
                    }
                }
            }
        }
        //luu so luong san pham cuar userLogin len session
        orderService.save(order, list);
        cartItemDBService.deleteCartItem(cartItemDBService.getCartID(user.getUserId()));
        List<CartItemDB> cartItemDBList = cartItemDBService.getCartUserLogin(cartItemDBService.getCartID(user.getUserId()));
        int totalProduct = cartItemDBList.stream().mapToInt(CartItemDB::getQuantity).sum();
        httpSession.setAttribute("cartTotalProduct", totalProduct);
        return "client/thankyou";
    }
}

