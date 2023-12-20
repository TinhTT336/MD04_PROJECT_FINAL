package com.tinh.controller.admin;

import com.tinh.model.entity.Category;
import com.tinh.model.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Value("/Users/admin/Desktop/PROJECT_MD4/src/main/webapp/uploads/images/")
    private String path;
    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/category", "/category/{i}"})
    public String pagination(Model model, @PathVariable(value = "i", required = false) Integer page, @RequestParam(value = "search-name", required = false) String searchName) {
        int currentPage = (page != null && page >= 1) ? page : 1;
        List<Category> categoryList;
        int totalPage;
        int totalCategorySearch = 0;
        if (searchName != null && !searchName.isEmpty()) {
            categoryList = categoryService.search(searchName);
            totalPage = categoryService.getTotalPageSearch("search-name");
            model.addAttribute("totalPage", totalPage);
            totalCategorySearch = categoryList.size();
        } else {
            categoryList = categoryService.pagination(5, currentPage);
            totalPage = categoryService.getTotalPagePagination(5, currentPage);
            model.addAttribute("totalPage", totalPage);
        }
        int activeCategory = categoryService.countCategory(true);
        int inactiveCategory = categoryService.countCategory(false);
        List<Category> parentCategories = categoryService.findParent();
        model.addAttribute("totalCategorySearch", totalCategorySearch);
        model.addAttribute("parentCategories", parentCategories);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("activeCategory", activeCategory);
        model.addAttribute("inactiveCategory", inactiveCategory);
        model.addAttribute("nameSearch", searchName);

        return "/admin/category/category-index";
    }

    @GetMapping("/category-add")
    public String addCategory(Model model) {
        Category category = new Category();
        List<Category> categories = categoryService.findParent();
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        return "/admin/category/category-add";
    }

    @PostMapping("/category-add")
    public String addCategoryPost(@Valid @ModelAttribute("category") Category category, BindingResult result,
                                  @RequestParam("img") MultipartFile file,Model model,
                                  RedirectAttributes redirectAttributes) {
        List<Category>categories=categoryService.findParent();
        if (result.hasErrors()) {
            model.addAttribute("categories",categories);
            return "/admin/category/category-add";
        }
        if (categoryService.checkCategoryNameExist(category.getCategoryName())) {
            model.addAttribute("categories",categories);
            redirectAttributes.addFlashAttribute("errName", "Category Name is existed!");
            return "redirect:/admin/category-add";
        } else if (categoryService.checkParentId(category.getParentId())) {
            model.addAttribute("categories",categories);
            redirectAttributes.addFlashAttribute("errParentId", "Please fill parentID!");
            return "redirect:/admin/category-add";
        }

        String fileName = file.getOriginalFilename();
        File destination = new File(path + fileName);
        category.setImage(fileName);
        boolean check = categoryService.saveOrUpdate(category);

        if (check) {
            try {
                file.transferTo(destination);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            redirectAttributes.addFlashAttribute("message", "Add category successfully!");
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/category-edit/{categoryId}")
    public String editCategory(@PathVariable("categoryId") Integer id, Model model) {
        Category category = categoryService.findById(id);
        List<Category> categories = categoryService.findParent();
        model.addAttribute("categories", categories);
        model.addAttribute("category", category);
        return "/admin/category/category-edit";
    }


    @PostMapping("category-edit")
    public String editCategoryPost(@Valid @ModelAttribute("category") Category category, BindingResult result,
                                   @RequestParam("img") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        Category updateCategory = categoryService.findById(category.getCategoryId());
        if (result.hasErrors()) {
            return "admin/category/category-edit";
        }

        if (categoryService.checkCategoryNameExistEdit(category.getCategoryName(), updateCategory.getCategoryName())) {
            redirectAttributes.addFlashAttribute("err", "Category Name is existed!");
            return "redirect:/admin/category";
        }
        System.out.println(file);
        System.out.println(file.getOriginalFilename());
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            File destination = new File(path + fileName);
            category.setImage(fileName);
            try {
                file.transferTo(destination);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            category.setImage(updateCategory.getImage());
        }
        boolean check = categoryService.saveOrUpdate(category);

        if (check) {
            redirectAttributes.addFlashAttribute("message", "Add category successfully!");
        }

        if (categoryService.saveOrUpdate(category)) {
            redirectAttributes.addFlashAttribute("message", "Category updated successfully!");
        }

        return "redirect:/admin/category";
    }

    @GetMapping("/category-delete/{categoryId}")
    public String changeStatus(@PathVariable("categoryId") Integer id, RedirectAttributes redirectAttributes) {
        categoryService.changeStatus(id);
        redirectAttributes.addFlashAttribute("message", "Change category's status successfully!");
        return "redirect:/admin/category";
    }

}
