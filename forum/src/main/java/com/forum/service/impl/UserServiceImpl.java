package com.forum.service.impl;

import com.forum.dao.UserMapper;
import com.forum.enums.ResponseEnum;
import com.forum.enums.RoleEnum;
import com.forum.pojo.User;
import com.forum.service.IUserService;
import com.forum.vo.ResponseVo;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  private UserMapper userMapper;


  @Override
  public ResponseVo<User> register(User user){

//    The user name cannot be repeated
    int countByUsername = userMapper.countByUsername(user.getUsername());
    if (countByUsername > 0){
//      throw new RuntimeException("该用户名已注册");
      return ResponseVo.error(ResponseEnum.USER_EXIST);
    }

//    The email cannot be repeated
    int countByEmail = userMapper.countByEmail(user.getEmail());
    if (countByEmail > 0) {
//      throw new RuntimeException("该邮箱已注册");
      return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
    }

    //set role
    user.setRole(RoleEnum.CUSTOMER.getCode());
    //MD5 摘要算法加密
    user.setPassword(DigestUtils.md5DigestAsHex(
        user.getPassword().getBytes(StandardCharsets.UTF_8)));

    //write into database
    int resultCount = userMapper.insertSelective(user);
    if (resultCount == 0){
//      throw new RuntimeException("注册失败");
      return ResponseVo.error(ResponseEnum.ERROR);
    }

    return ResponseVo.success();
  }

  @Override
  public ResponseVo<User> login(String username, String password){
    User user = userMapper.selectByUsername(username);
    if (user == null){
      //用户不存在
      return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
    }

    //密码核对
    if (!user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(
        password.getBytes(StandardCharsets.UTF_8)))){
      return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
    }

//    防止返回密码
    user.setPassword("");
    return ResponseVo.success(user);
  }

  //更换数据库中的用户头像路径
  @Override
  public void updateProfileImg(String username, String imgPath){

    User user = userMapper.selectByUsername(username);
    user.setProfile(imgPath);
    userMapper.updateByPrimaryKeySelective(user);
  }
}
