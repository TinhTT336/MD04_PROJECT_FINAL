package com.tinh.controller.admin;

import com.tinh.model.dao.product.ProductDaoImpl;
import com.tinh.model.dto.product.ProductDTO;
import com.tinh.model.entity.Category;
import com.tinh.model.entity.Image;
import com.tinh.model.entity.Product;
import com.tinh.model.service.category.CategoryService;
import com.tinh.model.service.image.ImageService;
import com.tinh.model.service.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.tinh.model.dao.product.ProductDaoImpl.totalPage;


@Controller
@RequestMapping("/admin")
public class ProductController {
    @Value("/Users/admin/Desktop/PROJECT_MD4/src/main/webapp/uploads/images/")
    private String path;
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;

    @GetMapping({"/product", "/product/{i}"})
    public String pagination(Model model, @PathVariable(value = "i", required = false) Integer page, @RequestParam(value = "search-name", required = false) String searchName) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Product> productList = new ArrayList<>();

        if (searchName != null && !searchName.isEmpty()) {
            productList = productService.search(searchName, 5, currentPage);
            int totalPageSearch = productService.totalPageSearch(searchName,5);
            model.addAttribute("totalPageSearch", totalPageSearch);
            int totalProductSearch = productService.totalProductSearch(searchName);
            model.addAttribute("totalProductSearch", totalProductSearch);
        } else {
            productList = productService.pagination(5, currentPage);
            int totalPage = ProductDaoImpl.totalPage;
            model.addAttribute("totalPage", totalPage);
        }
        int activeProduct = productService.countProductByStatus(true);
        int inactiveProduct = categoryService.countCategory(false);


        model.addAttribute("currentPage", currentPage);
        model.addAttribute("activeProduct", activeProduct);
        model.addAttribute("inactiveProduct", inactiveProduct);
        model.addAttribute("nameSearch", searchName);
        model.addAttribute("productList", productList);

        return "admin/product/product-index";
    }

    @GetMapping("/product/product-show/{id}")
    public String show(Model model, @PathVariable("id") Integer id) {
        Product product = productService.findById(id);
        List<Image> imageList = imageService.findByProductId(id);
        model.addAttribute("imageList", imageList);

        model.addAttribute("product", product);
        return "admin/product/product-show";
    }

    //su dung Product DTO
    @GetMapping("/product/product-add")
    public String add(Model model) {
        ProductDTO product = new ProductDTO();
        model.addAttribute("product", product);
        List<Category> categoryList = categoryService.findAll().stream().filter(Category::isCategoryStatus).collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);

        return "admin/product/product-add";
    }


    //su dung DTO
    @PostMapping("/product/product-add")
    public String addPost(@Valid
                          @ModelAttribute("product") ProductDTO productDTO, BindingResult result,
                          @RequestParam("imgs") MultipartFile[] multipartFiles,
                          RedirectAttributes redirectAttributes, Model model) {

        List<Category> categoryList = categoryService.findAll().stream().filter(cat -> cat.isCategoryStatus()).collect(Collectors.toList());
        if (result.hasErrors()) {
            model.addAttribute("categoryList", categoryList);
            return "admin/product/product-add";
        }
        if (productService.checkProductNameExist(productDTO.getProductName())) {
            redirectAttributes.addFlashAttribute("errName", "Product Name has been already existed");
            model.addAttribute("categoryList", categoryList);

            return "redirect:/admin/product/product-add";
        }

        if (productService.checkCategoryNull(productDTO.getCategory().getCategoryId())) {
            redirectAttributes.addFlashAttribute("errCat", "Please choose Category!");
            model.addAttribute("categoryList", categoryList);

            return "redirect:/admin/product/product-add";
        }

        MultipartFile file = productDTO.getImage();
        int productId;
        try {
            // Lưu sản phẩm và nhận lại ID mới
            String fileName = file.getOriginalFilename();
            File destination = new File(path + fileName);
            productDTO.setImage(file);
            productId = productService.save(productDTO);

            if (productId > 0) {
                //up ảnh sản phẩm
                file.transferTo(destination);

                // ảnh phụ sản phẩm
                for (MultipartFile multipartFile : multipartFiles) {
                    String multipartFileName = multipartFile.getOriginalFilename();
                    File destinationFile = new File(path + multipartFileName);
                    multipartFile.transferTo(destinationFile);

                    Image image = new Image();
                    image.setSrc(multipartFileName);
                    image.setProductId(productId);
                    imageService.saveOrUpdate(image);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (productId > 0) {
            redirectAttributes.addFlashAttribute("message", "Add product successfully!");
        }

        return "redirect:/admin/product";
    }

    @GetMapping("/product/product-edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);

        List<Category> categoryList = categoryService.findAll().stream().filter(cat-> cat.isCategoryStatus()).collect(Collectors.toList());
        model.addAttribute("categoryList", categoryList);

        List<Image> imageList = imageService.findByProductId(id);
        model.addAttribute("imageList", imageList);

        return "admin/product/product-update";
    }

    @PostMapping("/product/product-edit")
    public String editPost(@Valid @ModelAttribute("product") Product product,
                           BindingResult result, @RequestParam("img") MultipartFile file,
                           @RequestParam("imgs") MultipartFile[] files,
                           RedirectAttributes redirectAttributes, Model model) {
        Product updatedProduct = productService.findById(product.getProductId());
        List<Category> categoryList = categoryService.findAll();
        List<Image> updateImages = imageService.findByProductId(product.getProductId());

        if (result.hasErrors()) {
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("imageList", updateImages);

            return "admin/product/product-update";
        }
        if (productService.checkProductNameExist(product.getProductName())) {
            List<Product> productList = productService.findByName(product.getProductName());
            if (!productList.isEmpty()) {
                for (Product product1 : productList) {
                    if (product1.getProductId() != product.getProductId()) {
                        result.rejectValue("productName", "productName.exits", "Product Name has been already existed!");
                        model.addAttribute("categoryList", categoryList);
                        model.addAttribute("imageList", updateImages);

                        return "admin/product/product-update";
                    }
                }
            }

//            redirectAttributes.addFlashAttribute("errName", "Product Name is existed!");
        }

        boolean check;
        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File destination = new File(path + fileName);
                product.setImage(fileName);
                file.transferTo(destination);
            } else {
                product.setImage(updatedProduct.getImage());
            }
            check = productService.update(product);
            if (files.length > 0&& !files[0].isEmpty()) {
                imageService.delete(product.getProductId());
             int count =imageService.findByProductId(product.getProductId()).size();
                System.out.println(count);
                for (MultipartFile additionalImage : files) {
                    if (!additionalImage.isEmpty()) {
                        String additionalFileName = additionalImage.getOriginalFilename();
                        File additionalDestination = new File(path + additionalFileName);
                        additionalImage.transferTo(additionalDestination);

                        Image image = new Image();
                        image.setSrc(additionalFileName);
                        image.setProductId(product.getProductId());
                        imageService.saveOrUpdate(image);
                    }
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (check) {
            redirectAttributes.addFlashAttribute("message", "Update product successfully!");
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/product/product-change/{id}")
    public String changeStatus(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        productService.changeStatus(id);
        redirectAttributes.addFlashAttribute("message", "Change product's status successfully!");
        return "redirect:/admin/product";
    }

    @GetMapping({"/product/product-sort/asc","/product/product-sort/asc/{i}"})
    public String paginationAndSort(Model model, @PathVariable(value = "i", required = false) Integer page
    ) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Product> productList = productService.paginationAndSort(5, currentPage, "asc");
        int totalPageSortASC = productService.totalPageSort(5, currentPage, "asc");
        model.addAttribute("totalPageSortASC", totalPageSortASC);
        model.addAttribute("productList", productList);

        int activeProduct = productService.countProductByStatus(true);
        int inactiveProduct = categoryService.countCategory(false);


        model.addAttribute("currentPage", currentPage);
        model.addAttribute("activeProduct", activeProduct);
        model.addAttribute("inactiveProduct", inactiveProduct);
        return "admin/product/product-index";
    }

    @GetMapping({"/product/product-sort/desc","/product/product-sort/desc/{i}"})
    public String paginationAndSortDESC(Model model, @PathVariable(value = "i", required = false) Integer page
    ) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Product> productList = productService.paginationAndSort(5, currentPage, "desc");
        int totalPageSort = productService.totalPageSort(5, currentPage, "desc");

        model.addAttribute("totalPageSort", totalPageSort);
        model.addAttribute("productList", productList);
        int activeProduct = productService.countProductByStatus(true);
        int inactiveProduct = categoryService.countCategory(false);


        model.addAttribute("currentPage", currentPage);
        model.addAttribute("activeProduct", activeProduct);
        model.addAttribute("inactiveProduct", inactiveProduct);
        return "admin/product/product-index";
    }
}
