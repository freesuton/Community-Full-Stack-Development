package com.forum.dao;

import com.forum.pojo.PostCategory;
import java.util.List;

public interface PostCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PostCategory record);

    int insertSelective(PostCategory record);

    PostCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostCategory record);

    int updateByPrimaryKey(PostCategory record);

    List<PostCategory> selectAll();
}