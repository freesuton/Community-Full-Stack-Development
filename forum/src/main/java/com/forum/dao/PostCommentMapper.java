package com.forum.dao;

import com.forum.pojo.PostComment;
import java.util.List;

public interface PostCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PostComment record);

    int insertSelective(PostComment record);

    PostComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostComment record);

    int updateByPrimaryKey(PostComment record);

    List<PostComment> selectByPostId(Integer postId);

    int updateProfileImgByUsername(String username, String profileImg);
}