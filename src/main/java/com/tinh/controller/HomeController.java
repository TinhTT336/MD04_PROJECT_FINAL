package com.tinh.controller;

import com.tinh.model.entity.Category;
import com.tinh.model.entity.Product;
import com.tinh.model.service.category.CategoryService;
import com.tinh.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private HttpSession session;
    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Category> faceCategories = categoryService.findByParentId(1);
        List<Category> bodyCategories = categoryService.findByParentId(2);
        List<Category> hairCategories = categoryService.findByParentId(3);
        session.setAttribute("face",faceCategories);
        session.setAttribute(" body",bodyCategories);
        session.setAttribute("hair",hairCategories);
        List<Category>categoryList= categoryService.findAll().stream().filter(cat-> cat.isCategoryStatus()).limit(6).collect(Collectors.toList());
        model.addAttribute("categoryList",categoryList);

        List<Product>productList= productService.findAllActiveStatus(true).stream().sorted((pro1,pro2)-> (int) (pro2.getPrice()-pro1.getPrice())).limit(8).collect(Collectors.toList());
        model.addAttribute("productList",productList);

        return "home";
    }

    @RequestMapping("/blog")
    public String blog() {
        return "client/blog";
    }

    @RequestMapping("/blog-details")
    public String blogDetails() {
        return "client/blog-details";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "client/contact";
    }

    @RequestMapping("/thankyou")
    public String wishlist() {
        return "client/thankyou";
    }

    @GetMapping("/category")
    public String indexCategory(Model model){
        List<Category>categoryList=categoryService.findAll().stream().filter(cat-> cat.isCategoryStatus()).collect(Collectors.toList());
        model.addAttribute("categoryList",categoryList);
        return "client/category";
    }

}
