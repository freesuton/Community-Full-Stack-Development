package com.forum.service.impl;

import com.forum.dao.PostCommentMapper;
import com.forum.enums.ResponseEnum;
import com.forum.pojo.Post;
import com.forum.pojo.PostCategory;
import com.forum.pojo.PostComment;
import com.forum.service.IPostCommentService;
import com.forum.vo.PostCategoryVo;
import com.forum.vo.PostCommentVo;
import com.forum.vo.ResponseVo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostCommentServiceImpl implements IPostCommentService {

  @Autowired
  private PostCommentMapper postCommentMapper;

  @Override
  public ResponseVo<List<PostCommentVo>> selectByPostId(Integer postId) {
    //查询该推文的评论
//    List<PostCommentVo> postCommentVoList = new ArrayList<>();
    List<PostComment> postComments = postCommentMapper.selectByPostId(postId);

    //先把顶层评论取出来 即parentId=0
//    for (PostComment postComment : postComments){
//      if (postComment.getParentCommentId().equals(0)){
//        PostCommentVo postCommentVo = new PostCommentVo();
//        BeanUtils.copyProperties(postComment, postCommentVo);
//        postCommentVoList.add(postCommentVo);
//      }
//    }

    //先把顶层评论取出来 即parentId=0
    List<PostCommentVo> postCommentVoList = postComments.stream().filter(e -> e.getParentCommentId().equals(0))
        .map(e -> postCommentToPostCommentVo(e))
        .collect(Collectors.toList());

    //接下来查询子目录.不需要返回值，它会直接改变传过去的对象
    findSubCommentList(postCommentVoList,postComments);

    return ResponseVo.success(postCommentVoList);
  }

  private void findSubCommentList(List<PostCommentVo> postCommentVoList,List<PostComment> postComments){
    //从之前查到的该帖子中的所有回复中，匹配父类等于根目录的评论
    for (PostCommentVo postCommentVo: postCommentVoList){

      List<PostCommentVo> subCommentVoList = new ArrayList<>();
      for(PostComment postComment: postComments){
        //比较根目录的id和postComments里数据的parentId。如果查到内容，设置子回复
        if (postCommentVo.getId().equals(postComment.getParentCommentId())) {
          PostCommentVo subPostCommentVo = postCommentToPostCommentVo(postComment);
          subCommentVoList.add(subPostCommentVo);

        }
        postCommentVo.setSubComments(subCommentVoList);
      }
    }
  }

  private PostCommentVo postCommentToPostCommentVo(PostComment postComment){
    PostCommentVo postCommentVo = new PostCommentVo();
    BeanUtils.copyProperties(postComment, postCommentVo);
    return postCommentVo;
  }

  //发布文章
  @Override
  public ResponseVo<PostComment> createPostComment(PostComment postComment){

    //写入数据库
    int resultCount = postCommentMapper.insertSelective(postComment);

    if (resultCount == 0){
      return ResponseVo.error(ResponseEnum.ERROR);
    }

    return ResponseVo.success();
  }

  //更新评论中的头像
  public void updateProfileImgInPostComment(String username, String profileImg){
    postCommentMapper.updateProfileImgByUsername(username,profileImg);
  }
}
