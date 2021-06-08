package com.forum.dao;

import com.forum.pojo.Post;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

public interface PostMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Post record);

    int insertSelective(Post record);

    Post selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKey(Post record);

    List<Post> selectByPostCategoryIdSet(@Param("postCategoryIdSet") Set<Integer> postCategoryIdSet);

    int updateProfileImgByUsername(String username, String profileImg);
}