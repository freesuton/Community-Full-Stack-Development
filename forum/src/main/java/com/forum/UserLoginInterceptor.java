package com.forum;

import com.forum.consts.MallConst;
import com.forum.enums.ResponseEnum;
import com.forum.exception.UserLoginException;
import com.forum.pojo.User;
import com.forum.vo.ResponseVo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

//  true表示继续，false表示中断
  @Override
  public boolean preHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler)
      throws Exception {
    //跨域设置header
    response.setHeader("Access-Control-Allow-Credentials", "true");
//    response.setHeader("Access-Control-Allow-Origin", Tools.asList(whiteList.split(",")).contains(origin) ? origin : "-");
//    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT, OPTIONS");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "*");

    log.info("preHandle...");
    User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
    if (user==null){
      log.info("user=null");

      throw new UserLoginException();
//      return false;
//      return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }
    return true;
  }
}
