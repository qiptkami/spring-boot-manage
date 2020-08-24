package com.yiqiandewo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private Integer roleId;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
