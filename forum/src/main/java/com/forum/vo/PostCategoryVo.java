package com.forum.vo;

import java.util.List;
import lombok.Data;

@Data
public class PostCategoryVo {

  private Integer id;

  private Integer parentId;

  private String name;

  private Integer sortOrder;

  private List<PostCategoryVo> subCategories;
}
