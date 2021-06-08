package com.forum.service;

import com.forum.ForumApplicationTests;
import com.forum.enums.ResponseEnum;
import com.forum.vo.PostDetailVo;
import com.forum.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IPostServiceTest extends ForumApplicationTests {

  @Autowired
  private IPostService postService;

  @Test
  public void list() {
    ResponseVo<PageInfo> responseVo=postService.list(null,1,2);
    Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
  }

  @Test
  public void detail(){
    ResponseVo<PostDetailVo> responseVo = postService.detail(2);
    Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
  }

}