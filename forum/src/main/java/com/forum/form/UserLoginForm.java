package com.forum.form;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginForm {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

}
