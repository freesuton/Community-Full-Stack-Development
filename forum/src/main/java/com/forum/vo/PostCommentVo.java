package com.forum.vo;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class PostCommentVo {

  private Integer id;

  private Integer postId;

  private Integer parentCommentId;

  private String username;

  private String profile;

  private String parentCommentUsername;

  private String content;

  private Integer likeNum;

  private Integer commentNum;

  private Integer status;

  private Date createTime;

  private Date updateTime;

  private List<PostCommentVo> subComments;
}
