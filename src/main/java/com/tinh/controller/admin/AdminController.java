package com.tinh.controller.admin;

import com.tinh.model.service.category.CategoryService;
import com.tinh.model.service.order.OrderService;
import com.tinh.model.service.product.ProductService;
import com.tinh.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public String index(Model model){
        int categoryCount= categoryService.findAll().size();
        int productCount= productService.findAll().size();
        int userCount= userService.findAll().size();
//        int orderCount= orderService.findAll().size();
        model.addAttribute("categoryCount",categoryCount);
        model.addAttribute("productCount",productCount);
        model.addAttribute("userCount",userCount);
//        model.addAttribute("orderCount",orderCount);

        return "admin/index";
    }
}
