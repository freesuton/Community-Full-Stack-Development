package com.forum.service;

import com.forum.pojo.User;
import com.forum.vo.ResponseVo;

public interface IUserService {

  ResponseVo<User> register(User user);

  ResponseVo<User> login(String username, String password);

  void updateProfileImg(String username, String imgPath);
}
