package com.tinh.controller.admin;

import com.tinh.model.entity.User;
import com.tinh.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.tinh.model.dao.user.UserDaoImpl.*;


@Controller
@RequestMapping("/admin")
public class UsersController {
    @Autowired
    private UserService userService;

    @GetMapping({"/user", "/user/{i}"})
    public String index(Model model, @PathVariable(value = "i", required = false) Integer page, @RequestParam(value = "search", required = false) String searchName) {
        int currentPage = (page != null && page > 1) ? page : 1;
        List<User> userList;
//        int totalPage;
        int totalCatSearch = 0;
        if (searchName != null && !searchName.isEmpty()) {
            userList = userService.search(searchName);
            totalCatSearch = userList.size();
//            totalPage = userService.getTotalPageSearch(searchName);
            model.addAttribute("totalPage", totalPageSearch);
        } else {
            userList = userService.pagination(5, currentPage);
//            totalPage = userService.getTotalPagePagination(5, currentPage);
            model.addAttribute("totalPage", totalPagePagination);
        }

        int activeUser = userService.countUserByStatus(true);
        int blockedUser = userService.countUserByStatus(false);

        model.addAttribute("totalCatSearch", totalCatSearch);
        model.addAttribute("userList", userList);
        model.addAttribute("activeUser", activeUser);
        model.addAttribute("blockedUser", blockedUser);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("nameSearch", searchName);

        return "/admin/user/user-index";
    }

    @GetMapping("/user-change/{userId}")
    public String changeStatus(@PathVariable("userId") Integer id, RedirectAttributes redirectAttributes) {
        userService.changeStatus(id);
        User user = userService.findById(id);
        if (!user.isRole()) {
            redirectAttributes.addFlashAttribute("message", "Can't change ADMIN's status!");
        } else {
            if (user.isUserStatus()) {
                redirectAttributes.addFlashAttribute("message", "Blocked user!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Unblocked user!");
            }
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/user-set/{userId}")
    public String setRole(@PathVariable("userId") Integer id, RedirectAttributes redirectAttributes) {
        User user = userService.findById(id);
        userService.setRole(id);
        if (!user.isUserStatus()) {
            redirectAttributes.addFlashAttribute("message", "Can't change blocked user's role!");
        } else {
            if (user.isRole()) {
                redirectAttributes.addFlashAttribute("message", "Set role USER to ADMIN successfully!");
            } else {
                redirectAttributes.addFlashAttribute("message", "Set role ADMIN to USER successfully!");
            }
        }
        return "redirect:/admin/user";
    }

}
