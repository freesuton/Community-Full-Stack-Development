package com.forum.pojo;

import java.util.Date;
import lombok.Data;

@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String profile;

    private String phone;

    private String question;

    private String answer;

    private Integer role;

    private Date createTime;

    private Date updateTime;

}