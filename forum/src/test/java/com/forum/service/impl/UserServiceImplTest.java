package com.forum.service.impl;

import com.forum.enums.ResponseEnum;
import com.forum.enums.RoleEnum;
import com.forum.pojo.User;
import com.forum.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {

  @Autowired
  private UserServiceImpl userService;

  @Test
//  @Before
  public void register() {
//    User user = new User("jack","123456","qwew@qq.com", RoleEnum.CUSTOMER.getCode());
//    userService.register(user);
  }

  @Test
  public void login(){
    ResponseVo<User> responseVo = userService.login("admin", "123");
    Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
  }
}