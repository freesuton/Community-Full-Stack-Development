package com.forum.form;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostCommentForm {

  private Integer categoryId;

  @NotNull
  private Integer parentCommentId;

  @NotBlank
  private String username;

//  @NotBlank
  private String parentCommentUsername;

  @NotNull
  private Integer postId;

  @NotBlank
  private String content;

}
