package com.tinh.controller.user;

import com.tinh.model.dto.user.UserLoginDTO;
import com.tinh.model.entity.Order;
import com.tinh.model.entity.OrderDetail;
import com.tinh.model.entity.Product;
import com.tinh.model.entity.User;
import com.tinh.model.service.order.OrderService;
import com.tinh.model.service.product.ProductService;
import com.tinh.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ProfileController {
    @Value("/Users/admin/Desktop/PROJECT_MD4/src/main/webapp/uploads/images/")
    private String path;
    @Autowired
    HttpSession httpSession;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;


    @GetMapping("/order-history")
    public String orderHistory(Model model) {
        User userLogin = getProfile();
        if (userLogin != null) {
            model.addAttribute("user", userLogin);
            List<Order>orders=orderService.findByUserId(userLogin.getUserId());
            model.addAttribute("orders",orders);
        }else{
            return "redirect:/signin?action=profile";
        }
        return "client/profile/order-history";
    }

    @GetMapping("/order-show/{id}")
    public String showOrder(@PathVariable("id") Integer orderId, Model model) {
        User userLogin = getProfile();
        if (userLogin != null) {
            model.addAttribute("user", userLogin);
        }else{
            return "redirect:/signin?action=profile";
        }
        Order order = orderService.findByOrderId(orderId);
        model.addAttribute("order", order);

        List<OrderDetail> orderDetailList = orderService.findOrderDetailByOrderId(orderId);
        model.addAttribute("orderDetailList", orderDetailList);

        int cartTotal = (int) orderDetailList.stream().mapToDouble(orderDetail -> orderDetail.getOrderPrice() * orderDetail.getQuantity()).sum();
        model.addAttribute("cartTotal", cartTotal);
        return "client/profile/order-detail";
    }

    @GetMapping("/order-cancel/{id}")
    public String cancel(@PathVariable("id") Integer orderId, RedirectAttributes redirectAttributes) {
        orderService.changeStatus(2, orderId);
        List<OrderDetail> orderDetails = orderService.findOrderDetailByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            for (Product product : productService.findAll()) {
                if (orderDetail.getProduct().getProductId() == product.getProductId()) {
                    product.setStock(product.getStock() + orderDetail.getQuantity());
                    productService.save(product);
                }
            }
        }

        redirectAttributes.addFlashAttribute("message", "Cancelled order successfully!");
        return "redirect:/profile/edit-profile";
    }

    @GetMapping("/profile/edit-profile")
    public String editProfile(Model model) {
        User userLogin = getProfile();
        if (userLogin != null) {
            model.addAttribute("user", userLogin);
        }else{
            return "redirect:/signin?action=profile";
        }
        return "client/profile/edit-profile";
    }

    @PostMapping("/profile/edit-profile")
    public String postEditProfile(@Valid @ModelAttribute("user") User user, BindingResult result,
                                  @RequestParam("img") MultipartFile file,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "client/profile/edit-profile";
        }
        User userLogin = getProfile();
        if (!userService.checkPassword(user.getPassword(), userLogin)) {
            redirectAttributes.addFlashAttribute("errPassword", "Incorrect Password!");
            return "redirect:/profile/edit-profile";
        }

        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            File destination = new File(path + fileName);
            user.setAvatar(fileName);
            try {
                file.transferTo(destination);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            user.setAvatar(userLogin.getAvatar());
        }
        user.setUserId(userLogin.getUserId());
        userService.saveOrUpdate(user);
        redirectAttributes.addFlashAttribute("message", "Edit profile successfully!");

        return "redirect:/order-history";
    }

    @GetMapping("/profile/change-password")
    public String changePassword(Model model) {
        User userLogin = getProfile();
        if (userLogin != null) {
            model.addAttribute("user", userLogin);
        }else{
            return "redirect:/signin?action=profile";
        }
        return "client/profile/change-password";
    }

    @PostMapping("/profile/change-password")
    public String postChangePassword(@RequestParam("old-password") String oldPassword,
                                     @RequestParam("new-password") String newPassword,
                                     @RequestParam("confirm-password") String confirmPassword,
                                     RedirectAttributes redirectAttributes) {
        User userLogin = getProfile();
        if (oldPassword != null && newPassword != null && confirmPassword != null) {
            if (!userService.checkPassword(oldPassword, userLogin)) {
                redirectAttributes.addFlashAttribute("errOldPassword", "Incorrect old password!");
                return "redirect:/profile/change-password";
            }
            if (newPassword.length() < 4 || newPassword.length() > 12) {
                redirectAttributes.addFlashAttribute("errNewPassword", "New password's length is from 4 to 12");
                return "redirect:/profile/change-password";
            }
            if (newPassword.equals(oldPassword)) {
                redirectAttributes.addFlashAttribute("errNewPassword", "New password must be different from old password!");
                return "redirect:/profile/change-password";
            }
            if (!userService.checkConfirmedPassword(newPassword, confirmPassword)) {
                redirectAttributes.addFlashAttribute("errConfirmPassword", "Incorrect password!");
                return "redirect:/profile/change-password";
            }
        } else {
            redirectAttributes.addFlashAttribute("errConfirmPassword", "Please fill password!");
            return "redirect:/profile/change-password";
        }
        userService.changePassword(newPassword, userLogin.getUserId());
        redirectAttributes.addFlashAttribute("message", "Change password successfully!");

        return "redirect:/order-history";
    }

    public User getProfile() {
        UserLoginDTO userLoginDTO = (UserLoginDTO) httpSession.getAttribute("userLoginUser");
        if (userLoginDTO != null) {
            return userService.findById(userLoginDTO.getUserId());
        }
        return null;
    }
}
