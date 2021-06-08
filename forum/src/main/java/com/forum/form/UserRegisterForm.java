package com.forum.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterForm {


//  @NotNull
//  @NotEmpty 用于集合
//  意思是不能空白， 用于 判断String类型对象是否为空
  @NotBlank(message = "用户名不能为空")
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  private String email;
}
