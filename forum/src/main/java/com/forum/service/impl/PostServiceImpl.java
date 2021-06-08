package com.forum.service.impl;

import static com.forum.enums.PostStatusEnum.DELETE;
import static com.forum.enums.PostStatusEnum.OFF_SALE;
import static com.forum.enums.ResponseEnum.POST_HIDE_OR_DELETE;

import com.forum.dao.PostMapper;
import com.forum.enums.ResponseEnum;
import com.forum.pojo.Post;
import com.forum.service.IPostCategoryService;
import com.forum.service.IPostService;
import com.forum.vo.PostDetailVo;
import com.forum.vo.PostVo;
import com.forum.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostServiceImpl implements IPostService {

  @Autowired
  private IPostCategoryService postCategoryService;

  @Autowired
  private PostMapper postMapper;

  @Override
  public ResponseVo<PageInfo> list(Integer postCategoryId, Integer pageNum, Integer pageSize) {
    Set<Integer> postCategoryIdSet = new HashSet<>();
    if (postCategoryId != null){
      postCategoryService.findSubPostCategoryId(postCategoryId, postCategoryIdSet);
      //加入自身
      postCategoryIdSet.add(postCategoryId);
      //通过IdSet查询
    }

    //分页
    PageHelper.startPage(pageNum, pageSize);
    List<Post> postList = postMapper.selectByPostCategoryIdSet(postCategoryIdSet);
    List<PostVo> postVoList = postList
        .stream()
        .map(e -> {
          PostVo postVo = new PostVo();
          BeanUtils.copyProperties(e, postVo);
          return postVo;
        }).collect(Collectors.toList());

    PageInfo pageInfo = new PageInfo<>(postList);
    pageInfo.setList(postVoList);
    return ResponseVo.success(pageInfo);

  }

  //post detail
  @Override
  public ResponseVo<PostDetailVo> detail(Integer postId){
    Post post = postMapper.selectByPrimaryKey(postId);

    //只对确定性条件判断
    if (post.getStatus().equals(OFF_SALE.getCode())
        || post.getStatus().equals(DELETE.getCode())){
      return ResponseVo.error(POST_HIDE_OR_DELETE);
    }
    PostDetailVo postDetailVo = new PostDetailVo();
    BeanUtils.copyProperties(post, postDetailVo);
    //敏感数据处理如果不希望某个字段直接显示可增加以下代码
//    postDetailVo.setCommentNum(post.getCommentNum()> 100 ? 100 : post.getCommentNum());
    return ResponseVo.success(postDetailVo);
  }

  //发布文章
  @Override
  public ResponseVo<Post> createPost(Post post){

    //写入数据库
    int resultCount = postMapper.insertSelective(post);

    if (resultCount == 0){
      return ResponseVo.error(ResponseEnum.ERROR);
    }

    return ResponseVo.success();
  }

  //更新帖子中的头像
  public void updateProfileImgInPost(String username, String profileImg){
    postMapper.updateProfileImgByUsername(username,profileImg);
  }
}
