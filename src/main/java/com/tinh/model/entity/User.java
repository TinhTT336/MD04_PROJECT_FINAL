package com.tinh.model.entity;

public class User {
    private int userId;
    private String username;
    private String email;
    private String address;
    private String phone;
    private boolean role =true;
    private boolean userStatus =true;
    private String avatar;
    private String password;

    public User() {
    }

    public User(int userId, String username, String email, String address, String phone, boolean role, boolean userStatus, String avatar, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.userStatus = userStatus;
        this.avatar = avatar;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", userStatus=" + userStatus +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
