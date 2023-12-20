package com.tinh.model.dto.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterDTO {
    @NotEmpty(message = "Please fill username!")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",message = "Please include @ in email!")
    private String email;
    @NotEmpty(message = "Please fill address!")
    private String address;
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b",message = "Phone start with 84, 03(5|7|8|9 and length form 10 to 11 characters")
    private String phone;
    @Size(min = 4,max = 12,message = "Password's length is from 4 to 12")
    private String password;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String username, String email, String address, String phone, String password) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
