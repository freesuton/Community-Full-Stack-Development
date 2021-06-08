package com.forum.controller;

import static com.forum.enums.ResponseEnum.PARAM_ERROR;

import com.forum.consts.MallConst;
import com.forum.form.CreatePostForm;
import com.forum.form.PostCommentForm;
import com.forum.pojo.Post;
import com.forum.pojo.PostComment;
import com.forum.pojo.User;
import com.forum.service.IPostCommentService;
import com.forum.vo.PostCommentVo;
import com.forum.vo.ResponseVo;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(allowCredentials = "true")
@RestController
@Slf4j
public class PostCommentController {

  @Autowired
  private IPostCommentService postCommentService;


  @GetMapping("/postComments/{postId}")
  public ResponseVo<List<PostCommentVo>> selectByPostId(@PathVariable Integer postId){
    return postCommentService.selectByPostId(postId);
  }

  //创建评论
  @PostMapping("/createPostComment")
  public ResponseVo<PostComment> createPostComment(@Valid @RequestBody PostCommentForm postCommentForm, BindingResult bindingResult,
      HttpSession session){

    //读取登陆信息
    User user = (User) session.getAttribute(MallConst.CURRENT_USER);

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

    PostComment postComment = new PostComment();
    BeanUtils.copyProperties(postCommentForm, postComment);
    //dto
    return postCommentService.createPostComment(postComment);
  }

}
