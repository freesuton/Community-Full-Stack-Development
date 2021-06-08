package com.forum.controller;

import static com.forum.enums.ResponseEnum.PARAM_ERROR;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.forum.consts.MallConst;
import com.forum.form.CreatePostForm;
import com.forum.pojo.Post;
import com.forum.pojo.User;
import com.forum.service.IPostService;
import com.forum.vo.PostDetailVo;
import com.forum.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(allowCredentials = "true")
@RestController
@Slf4j
public class PostController {

  @Autowired
  private IPostService postService;

  //RequestParam用接受url参数，RequestBody接受json参数
  //用url传递参数，所以用@RequestParam，并且不是必填，所以要false。
  //获取文章列表
  @GetMapping("/posts")
  public ResponseVo<PageInfo> list(@RequestParam(required = false) Integer postCategoryId,
                                   @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(required = false, defaultValue = "3") Integer pageSize){

    return postService.list(postCategoryId, pageNum, pageSize);
  }

  //读取文章详情
  @GetMapping("/posts/{postId}")
  public ResponseVo<PostDetailVo> detail(@PathVariable Integer postId){
    return postService.detail(postId);
  }

  //图片上传,form-data 用RequestParam注解接受,value可省略，确保参数名 和前端传来的数据名称一致
  @PostMapping("/postImage")
  public JSONObject postImage( MultipartFile file){

    JSONObject result = new JSONObject();
    if (file.isEmpty()){
      result.put("errno", 1);
      return result;
    }
//    System.out.println(file.getOriginalFilename());

    String fileName = file.getOriginalFilename(); //获取文件名
    String suffixName = fileName.substring(fileName.lastIndexOf("."));//截取后缀
    String filePath = "/Users/hao/Desktop/project-h/public/imgsForForum";
    fileName = UUID.randomUUID() + suffixName; //新文件名
    File  dest = new File(filePath +"/"+ fileName);
    if (!dest.getParentFile().exists()){
      dest.getParentFile().mkdir();
    }
    try {
      file.transferTo(dest);//将文件保存到dest目录下
    }catch (IOException e){
      e.printStackTrace();
    }

    result.put("errno", 0);
    List<String> urls = Arrays.asList("/imgsForForum/"+ fileName);
    result.put("data", urls);
    return result;
  }

  //富文本内容接口
//  @PostMapping("createPost")
//  public ResponseVo<PostDetailVo> createPost(){
//
//  }
  @PostMapping("/createPost")
  public ResponseVo<Post> createPost(@Valid @RequestBody CreatePostForm createPostForm,
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

    Post post = new Post();
    BeanUtils.copyProperties(createPostForm, post);
    //dto
    return postService.createPost(post);
  }
}



