package com.forum.service;

import com.forum.vo.PostCategoryVo;
import com.forum.vo.ResponseVo;
import java.util.List;
import java.util.Set;

public interface IPostCategoryService {

  ResponseVo<List<PostCategoryVo>> selectAll();

  void findSubPostCategoryId(Integer id, Set<Integer> resultSet);

}
