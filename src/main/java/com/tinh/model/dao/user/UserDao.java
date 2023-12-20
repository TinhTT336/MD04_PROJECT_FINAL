package com.tinh.model.dao.user;

import com.tinh.model.dao.IGenericDao;
import com.tinh.model.dto.user.UserCheckoutDTO;
import com.tinh.model.dto.user.UserLoginDTO;
import com.tinh.model.dto.user.UserRegisterDTO;
import com.tinh.model.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    boolean saveOrUpdate(User user);

    User findById(Integer id);

    List<User> pagination(Integer a, Integer b);

    void changeStatus(Integer id);

    void setRole(Integer id);

    User findByMail(String mail);

    Integer countUserByStatus(Boolean b);

    List<User> search(String searchName);

    Boolean register(UserRegisterDTO user);

void changePassword(String password,Integer userId);
}
