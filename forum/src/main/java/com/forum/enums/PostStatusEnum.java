package com.forum.enums;

import com.forum.pojo.Post;
import lombok.Getter;

/*
 推文状态：1-显示，2-隐藏，3-删除

 */
@Getter
public enum PostStatusEnum {

  ON_SALE(1),

  OFF_SALE(2),

  DELETE(3)

  ;

  Integer code;

  PostStatusEnum(Integer code){
    this.code = code;
  }
}
