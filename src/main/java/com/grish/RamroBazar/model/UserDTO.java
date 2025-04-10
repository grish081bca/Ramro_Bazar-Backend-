package com.grish.RamroBazar.model;

import lombok.Data;

@Data
public class UserDTO {
    private Integer userId;
    private String userName;
    private String password;
    private String role;
    private String userEmail;
    private String userPhone;
}
