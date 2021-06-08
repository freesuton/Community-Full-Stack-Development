package com.forum.pojo;

import java.util.Date;
import lombok.Data;

@Data
public class Post {
    private Integer id;

    private Integer categoryId;

    private String username;

    private String profile;

    private Integer commentId;

    private String title;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private String content;

    private String description;

    private Integer likeNum;

    private Integer commentNum;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}