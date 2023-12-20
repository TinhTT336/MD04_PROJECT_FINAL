package com.tinh.controller;

import com.tinh.model.dto.user.UserLoginDTO;
import com.tinh.model.dto.user.UserRegisterDTO;
import com.tinh.model.entity.CartItemDB;
import com.tinh.model.entity.User;
import com.tinh.model.service.cart.CartItemDBService;
import com.tinh.model.service.user.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller

public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    HttpSession session;
    @Autowired
    private CartItemDBService cartItemDBService;

//    @GetMapping("/signup")
//    public String signup(Model model) {
//        User user = new User();
//        model.addAttribute("user", user);
//        return "client/signup";
//    }

    @GetMapping("/signup")
    public String signup(Model model) {
        UserRegisterDTO user = new UserRegisterDTO();
        model.addAttribute("user", user);
        return "client/signup";
    }

    @PostMapping("/signup-post")
    public String signupPost(@Valid @ModelAttribute("user") UserRegisterDTO user,
                             BindingResult result, @RequestParam("confirm-password") String confirmedPassword,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "client/signup";
        }
        if (userService.findByMail(user.getEmail()) != null) {
            redirectAttributes.addFlashAttribute("errEmail", "Email has already been existed!");
            return "redirect:/signup";
        }
        if (!userService.checkConfirmedPassword(user.getPassword(), confirmedPassword)) {
            redirectAttributes.addFlashAttribute("errRePassword", "Incorrect password!");
            return "redirect:/signup";
        }
        if (userService.register(user)) {
            redirectAttributes.addFlashAttribute("message", "Create account successfully!");
        }
        return "redirect:/signin";
    }


    @GetMapping("/signin")
    public String signin(Model model) {
        User user = new User();
        model.addAttribute("user", user);
//        model.addAttribute("action", action);
        return "client/signin";
    }

    @PostMapping("/signin")
    public String signinPost(@Valid @ModelAttribute("user") User user, BindingResult result,
                             RedirectAttributes redirectAttributes,
                             @RequestParam(value = "action", required = false) String action) {
        UserLoginDTO userLogin = userService.login(user.getEmail(), user.getPassword());

        if (userLogin != null) {
            if (!userLogin.isUserStatus()) {
                redirectAttributes.addFlashAttribute("message", "Account is blocked, please contact admin!!!");
            } else {
                if (userLogin.isRole()) {
                    session.setAttribute("userLoginUser", userLogin);
                    redirectAttributes.addFlashAttribute("message", "Login successfully!");

                    //luu so luong san pham cuar userLogin len session
                    List<CartItemDB> list = cartItemDBService.getCartUserLogin(cartItemDBService.getCartID(userLogin.getUserId()));
                    int totalProduct = list.stream().mapToInt(CartItemDB::getQuantity).sum();
                    session.setAttribute("cartTotalProduct", totalProduct);

                    if (action == null) {
                        action = "";
                    }
                    switch (action) {
                        case "product":
                            return "redirect:/product";
                        case "cart":
                            return "redirect:/cart";
                        case "checkout":
                            return "redirect:/checkout";
                        case "wishlist":
                            return "redirect:/wishlist";
                        case "profile":
                            return "redirect:/order-history";
                        default:
                            redirectAttributes.addFlashAttribute("message", "Login successfully!");

                            return "redirect:/";
                    }
                } else {
                    session.setAttribute("userLoginAdmin", userLogin);
                    redirectAttributes.addFlashAttribute("message", "Login successfully!");

                    return "redirect:/admin";
                }
            }
        }
        redirectAttributes.addFlashAttribute("err", "Email or Password is invalid!!!");
        return "redirect:/signin";
    }

    @GetMapping("/logout-user")
    public String logoutUser() {
        session.removeAttribute("userLoginUser");
        session.removeAttribute("cartTotalProduct");
        return "redirect:/?error=true";
    }

    @GetMapping("/logout-admin")
    public String logoutAdmin() {
        session.removeAttribute("userLoginAdmin");
        return "redirect:/?error=true";
    }
}
