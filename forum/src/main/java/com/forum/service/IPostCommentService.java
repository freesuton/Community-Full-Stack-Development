package com.forum.service;

import com.forum.pojo.Post;
import com.forum.pojo.PostComment;
import com.forum.vo.PostCategoryVo;
import com.forum.vo.PostCommentVo;
import com.forum.vo.ResponseVo;
import java.util.List;
import java.util.Set;

public interface IPostCommentService {

  ResponseVo<List<PostCommentVo>> selectByPostId(Integer postId);

  //crateComment
  ResponseVo<PostComment> createPostComment(PostComment postComment);

  //更新头像
  void updateProfileImgInPostComment(String username, String profileImg);
}
