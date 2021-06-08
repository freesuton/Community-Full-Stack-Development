package com.forum.service;

import com.forum.pojo.Post;
import com.forum.vo.PostDetailVo;
import com.forum.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

public interface IPostService {

  ResponseVo<PageInfo> list(Integer categoryId,Integer pageNum, Integer pageSize);

  ResponseVo<PostDetailVo> detail(Integer postId);

  //cratePost
  ResponseVo<Post> createPost(Post post);

  //更新头像
  void updateProfileImgInPost(String username, String profileImg);
}
