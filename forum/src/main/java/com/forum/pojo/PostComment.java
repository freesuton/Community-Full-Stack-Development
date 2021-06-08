package com.forum.pojo;

import java.util.Date;
import lombok.Data;

@Data
public class PostComment {

    private Integer id;

    private Integer parentCommentId;

    private String username;

    private String profile;

    private String parentCommentUsername;

    private Integer postId;

    private String content;

    private Integer likeNum;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer commentNum;

}