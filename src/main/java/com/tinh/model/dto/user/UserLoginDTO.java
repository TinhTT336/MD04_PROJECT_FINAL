package com.tinh.model.dto.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserLoginDTO {
    private int userId;
    @NotEmpty(message = "Please fill username!")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",message = "Please include @ in email!")
    private String email;
    @NotEmpty(message = "Please fill username!")
    @Size(min = 4,max = 12,message = "Password's length is from 4 to 12")

    private String password;
    private String username;
    private boolean userStatus =true;
    private boolean role =true;

    public UserLoginDTO() {
    }

    public UserLoginDTO(int userId, String email, String password, String username, boolean userStatus, boolean role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.username = username;
        this.userStatus = userStatus;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
