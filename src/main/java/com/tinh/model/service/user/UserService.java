package com.tinh.model.service.user;

import com.tinh.model.dto.user.UserLoginDTO;
import com.tinh.model.dto.user.UserRegisterDTO;
import com.tinh.model.entity.User;
import com.tinh.model.service.IGenericService;

import java.util.List;

public interface UserService extends IGenericService<User, Integer, String> {
    void setRole(Integer id);

    User findByMail(String mail);

    Integer countUserByStatus(Boolean b);

    List<User> search(String searchName);

    Boolean register(UserRegisterDTO user);

    UserLoginDTO login(String email, String password);

    Boolean checkPassword(String password,User user);

    Boolean checkConfirmedPassword(String password, String confirmedPassword);
    void changePassword(String password, Integer userId);
}