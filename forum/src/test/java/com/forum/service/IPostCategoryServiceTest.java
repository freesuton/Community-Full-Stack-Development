package com.forum.service;

import static org.junit.Assert.*;

import com.forum.ForumApplicationTests;
import com.forum.enums.ResponseEnum;
import com.forum.vo.PostCategoryVo;
import com.forum.vo.ResponseVo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class IPostCategoryServiceTest extends ForumApplicationTests {

  @Autowired
  private IPostCategoryService postCategoryService;

  @Test
  public void selectAll() {

    ResponseVo<List<PostCategoryVo>> responseVo = postCategoryService.selectAll();
    Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
  }

  @Test
  public void findSubPostCategoryId(){
    Set<Integer> set = new HashSet<>();
    postCategoryService.findSubPostCategoryId(1001,set);
    log.info("set={}", set);
  }
}