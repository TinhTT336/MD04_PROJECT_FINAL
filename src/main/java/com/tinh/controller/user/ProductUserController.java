package com.tinh.controller.user;

import com.tinh.model.dto.user.UserLoginDTO;
import com.tinh.model.entity.CartItemDB;
import com.tinh.model.entity.Category;
import com.tinh.model.entity.Image;
import com.tinh.model.entity.Product;
import com.tinh.model.service.cart.CartItemDBService;
import com.tinh.model.service.category.CategoryService;
import com.tinh.model.service.image.ImageService;
import com.tinh.model.service.product.ProductService;
import com.tinh.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductUserController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartItemDBService cartItemDBService;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSession httpSession;

    @GetMapping({"/product", "/product/{i}"})
    public String pagination(Model model, @PathVariable(value = "i", required = false) Integer page, @RequestParam(value = "search-name", required = false) String searchName) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Product> productList = new ArrayList<>();

        if (searchName != null && searchName.isEmpty()) {
            productList = productService.searchActive(searchName, 8, currentPage);
            int totalPageSearch = productService.totalPageSearchActive(searchName, 8);
            model.addAttribute("totalPageSearch", totalPageSearch);
            int totalProductSearch = productService.totalProductSearchActive(searchName);
            model.addAttribute("totalProductSearch", totalProductSearch);
        } else {
            productList = productService.pagination(8, currentPage);
            int totalPage = productService.totalPageActive(8, currentPage);
            model.addAttribute("totalPage", totalPage);
        }
        List<Category> categoryList = categoryService.findAll().stream().filter(cat -> cat.isCategoryStatus()).collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("nameSearch", searchName);
        model.addAttribute("productList", productList);

        //luu so luong san pham cuar userLogin len session
        UserLoginDTO userLogin = (UserLoginDTO) httpSession.getAttribute("userLoginUser");

       if(userLogin!=null){
           List<CartItemDB> list = cartItemDBService.getCartUserLogin(cartItemDBService.getCartID(userLogin.getUserId()));
           int totalProduct = list.stream().mapToInt(CartItemDB::getQuantity).sum();
           httpSession.setAttribute("cartTotalProduct", totalProduct);
       }

        return "client/product-list";
    }

    @GetMapping({"/product/product-sort/asc","/product/product-sort/asc/{i}"})
    public String paginationAndSort(Model model, @PathVariable(value = "i", required = false) Integer page
                                   ) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Product> productList = productService.paginationAndSortActive(8, currentPage, "asc");
        int totalPageSortASC = productService.totalPageActiveSort(8, currentPage, "asc");
        List<Category> categoryList = categoryService.findAll().stream().filter(cat -> cat.isCategoryStatus()).collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("totalPageSortASC", totalPageSortASC);
        model.addAttribute("productList", productList);
        return "client/product-list";
    }

    @GetMapping({"/product/product-sort/desc","/product/product-sort/desc/{i}"})
    public String paginationAndSortDESC(Model model, @PathVariable(value = "i", required = false) Integer page
    ) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Product> productList = productService.paginationAndSortActive(8, currentPage, "desc");
        int totalPage = productService.totalPageActiveSort(8, currentPage, "desc");
        List<Category> categoryList = categoryService.findAll().stream().filter(cat -> cat.isCategoryStatus()).collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("totalPageSort", totalPage);
        model.addAttribute("productList", productList);
        return "client/product-list";
    }

    @GetMapping("/product-detail/{id}")
    public String showProductDetail(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        List<Category> categoryList = categoryService.findAll().stream().filter(cat -> cat.isCategoryStatus()).collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);

        List<Image> images = imageService.findByProductId(id);
        model.addAttribute("images", images);

        List<Product>productList= productService.findAllActiveStatus(true).stream().sorted((pro1,pro2)-> (int) (pro2.getPrice()-pro1.getPrice())).limit(4).collect(Collectors.toList());
        model.addAttribute("productList",productList);

        return "client/product-details";
    }

    @GetMapping("/product-by-category/{id}")
    public String showProductByCategoryId(@PathVariable("id") Integer id, Model model) {
        List<Product> productList =  productService.findByCategoryId(id).stream().filter(pro -> pro.isProductStatus()).collect(Collectors.toList());
        model.addAttribute("productList", productList);
        List<Category> categoryList = categoryService.findAll().stream().filter(cat -> cat.isCategoryStatus()).collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);

        return "client/product-list";
    }

}
