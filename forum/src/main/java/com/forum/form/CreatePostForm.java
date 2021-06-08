package com.forum.form;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePostForm {

  private Integer categoryId;


  private String username;


  private String title;

  private String mainImage;

  private String subImages;


  private String content;

  private String description;
}
