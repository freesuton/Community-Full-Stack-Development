package com.forum.service.impl;

import static com.forum.consts.MallConst.ROOT_PARENT_ID;
import com.forum.dao.PostCategoryMapper;
import com.forum.pojo.PostCategory;
import com.forum.service.IPostCategoryService;
import com.forum.vo.PostCategoryVo;
import com.forum.vo.ResponseVo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import jdk.jfr.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostCategoryServiceImpl implements IPostCategoryService {

  /**
   * 耗时：http（请求微信api）> 磁盘 > 内存
   * mysql(内网+磁盘)
   */

  @Autowired
  private PostCategoryMapper postCategoryMapper;

  @Override
  public ResponseVo<List<PostCategoryVo>> selectAll(){
    List<PostCategory> postCategories = postCategoryMapper.selectAll();

    //查处parent_id=0 的数据
//    for (PostCategory postCategory : postCategories){
//      if (postCategory.getParentId().equals( ROOT_PARENT_ID)){
//        PostCategoryVo postCategoryVo = new PostCategoryVo();
//       //因为postCategory和Vo对象里属性一样，把postCategory里的对象拷贝到Vo里
//        BeanUtils.copyProperties(postCategory,postCategoryVo);
//        postCategoryVoList.add(postCategoryVo);
//      }
//    }
    //stream循环list中每一个元素，filter返回true和false来判断是否过滤，
    // map可以使每个元素执行括号里的方法，collect把集合转换成list
    //:: 表示引用
    List<PostCategoryVo> postCategoryVoList = postCategories.stream()
        .filter(e -> e.getParentId().equals(ROOT_PARENT_ID))
        .map(this::postCategoryToPostCategory)
        .sorted(Comparator.comparing(PostCategoryVo::getSortOrder).reversed())
        .collect(Collectors.toList());

    //查询子目录
    findSubPostCategory(postCategoryVoList,postCategories);

    return ResponseVo.success(postCategoryVoList);
  }

  @Override
  public void findSubPostCategoryId(Integer id, Set<Integer> resultSet){
    List<PostCategory> postCategories = postCategoryMapper.selectAll();
    findSubPostCategoryId(id, resultSet,postCategories);
  }

  public void findSubPostCategoryId(Integer id, Set<Integer> resultSet, List<PostCategory> postCategories){

    for (PostCategory postCategory : postCategories){
      if (postCategory.getParentId().equals(id)){
        resultSet.add(postCategory.getId());
        findSubPostCategoryId(postCategory.getId(), resultSet,postCategories);
      }
    }
  }


  private void findSubPostCategory(List<PostCategoryVo> postCategoryVoList, List<PostCategory> postCategories){
    for (PostCategoryVo postCategoryVo : postCategoryVoList){
      List<PostCategoryVo> subPostCategoryVoList = new ArrayList<>();
      for (PostCategory postCategory: postCategories){
        //如果查到内容，设置subCategory，继续往下查
        if (postCategoryVo.getId().equals(postCategory.getParentId())){
          PostCategoryVo subPostCategoryVo = postCategoryToPostCategory(postCategory);
          subPostCategoryVoList.add(subPostCategoryVo);
        }

        //排序
        subPostCategoryVoList.sort(Comparator.comparing(PostCategoryVo::getSortOrder).reversed());
        postCategoryVo.setSubCategories(subPostCategoryVoList);

        //递归继续查下去
        findSubPostCategory(subPostCategoryVoList,postCategories);
      }
    }
  }

  private PostCategoryVo postCategoryToPostCategory(PostCategory postCategory){
    PostCategoryVo postCategoryVo = new PostCategoryVo();
    BeanUtils.copyProperties(postCategory,postCategoryVo);
    return postCategoryVo;
  }
}
