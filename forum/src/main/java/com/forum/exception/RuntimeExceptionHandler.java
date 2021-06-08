package com.forum.exception;

import com.forum.enums.ResponseEnum;
import com.forum.vo.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//用来捕获异常
@ControllerAdvice
public class RuntimeExceptionHandler {

  //要捕获的类
  @ExceptionHandler(RuntimeException.class)
  //要返回json格式
  @ResponseBody
//  @ResponseStatus(HttpStatus.FORBIDDEN)用来返回状态
  public ResponseVo handle(RuntimeException e){
    return ResponseVo.error(ResponseEnum.ERROR, e.getMessage());
  }

  @ExceptionHandler(UserLoginException.class)
  @ResponseBody
  public ResponseVo userLoginHandle(){
    return ResponseVo.error(ResponseEnum.NEED_LOGIN);
  }

}
