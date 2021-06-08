package com.forum.controller;


import com.forum.pojo.PostCategory;
import com.forum.service.IPostCategoryService;
import com.forum.vo.PostCategoryVo;
import com.forum.vo.ResponseVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostCategoryController {

  @Autowired
  private IPostCategoryService postCategoryService;

  @GetMapping("/postCategories")
  public ResponseVo<List<PostCategoryVo>> selectAll(){
    return postCategoryService.selectAll();
  }
}
