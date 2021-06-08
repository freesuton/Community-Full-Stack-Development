package com.forum.controller;

import static com.forum.enums.ResponseEnum.PARAM_ERROR;

import com.alibaba.fastjson.JSONObject;
import com.forum.consts.MallConst;
import com.forum.enums.ResponseEnum;
import com.forum.form.UserLoginForm;
import com.forum.form.UserRegisterForm;
import com.forum.pojo.User;
import com.forum.service.IPostCommentService;
import com.forum.service.IPostService;
import com.forum.service.IUserService;
import com.forum.vo.ResponseVo;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(allowCredentials = "true")
@RestController
//@RequestMapping("/user")
@Slf4j
public class UserController {

  @Autowired
  private IUserService userService;

  @Autowired
  private IPostService postService;

  @Autowired
  private IPostCommentService postCommentService;

  @PostMapping("/user/register")
//  @RequestParam 代表必须传来的数据
  public ResponseVo<User> register(@Valid @RequestBody UserRegisterForm userRegisterForm,
                              BindingResult bindingResult){
    if (bindingResult.hasErrors()){
      //   一定要用log输出在控制台，不要用sysyout，不然会打印到页面上去
//    log.info("username{}", userName);
//    json格式
      log.error("注册提交的参数有误，{} {}", Objects.requireNonNull(
          bindingResult.getFieldError()).getField(),
          bindingResult.getFieldError().getDefaultMessage());
      return ResponseVo.error(PARAM_ERROR, bindingResult);
    }

//    前端传给controller的用form，后端传给数据库的用pojo，所以要转换
    User user = new User();
    BeanUtils.copyProperties(userRegisterForm, user);
    //dto
    return userService.register(user);
  }

  @PostMapping("/user/login")
  public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  BindingResult bindingResult,
                                  HttpSession session){

    if (bindingResult.hasErrors()){
      return ResponseVo.error(PARAM_ERROR, bindingResult);
    }

    ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

    //设置session
    session.setAttribute(MallConst.CURRENT_USER, userResponseVo.getData());
    log.info("/login sessionId={}", session.getId());
    return userResponseVo;
  }

  //session保存在内存里，改进版token（sessionid） + redis
  @GetMapping("/user")
  public ResponseVo<User> userInfo(HttpSession session){
    log.info("/login sessionId={}", session.getId());
    User user = (User) session.getAttribute(MallConst.CURRENT_USER);
    if (user==null){
      return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }
//    若要拿到最新的数据，可再查询一次，但一般没必要
    return ResponseVo.success(user);
  }

  //TODO 判断登陆状态 ，拦截器
  @PostMapping("/user/logout")
  public ResponseVo logout(HttpSession session){
    log.info("/user/logout sessionId={}", session.getId());
    session.removeAttribute(MallConst.CURRENT_USER);
    return ResponseVo.success();
  }

  //头像上传,form-data 用RequestParam注解接受,value可省略，确保参数名 和前端传来的数据名称一致
  @PostMapping("/profileImg")
  public JSONObject postImage( MultipartFile file,HttpSession session) {

    JSONObject result = new JSONObject();
    if (file.isEmpty()) {
      result.put("errno", 1);
      return result;
    }
//    System.out.println(file.getOriginalFilename());

    String fileName = file.getOriginalFilename(); //获取文件名
    String suffixName = fileName.substring(fileName.lastIndexOf("."));//截取后缀
    String filePath = "/Users/hao/Desktop/project-h/public/imgsForForum/userProfileImg";
    fileName = UUID.randomUUID() + suffixName; //新文件名
    File dest = new File(filePath + "/" + fileName);
    if (!dest.getParentFile().exists()) {
      dest.getParentFile().mkdir();
    }
    try {
      file.transferTo(dest);//将文件保存到dest目录下
    } catch (IOException e) {
      e.printStackTrace();
    }

    result.put("errno", 0);
    List<String> urls = Arrays.asList("/imgsForForum/userProfileImg/" + fileName);
    result.put("data", urls);

    //修改数据库种的头像路径(调用服务层)
    User user = (User) session.getAttribute(MallConst.CURRENT_USER);
    userService.updateProfileImg(user.getUsername(),urls.get(0));

    //修改帖子的中该用户的头像
    postService.updateProfileImgInPost(user.getUsername(),urls.get(0));
    //修改评论中的用户头像
    postCommentService.updateProfileImgInPostComment(user.getUsername(),urls.get(0));

    return result;
  }
}
