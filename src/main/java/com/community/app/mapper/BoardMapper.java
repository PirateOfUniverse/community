package com.community.app.mapper;

import com.community.app.domain.Notice;
import com.community.app.domain.Post;
import com.community.app.domain.Reply;
import com.community.app.dto.HeartVO;
import com.community.app.dto.PagingVO;
import com.community.app.dto.PostUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    public int writePost(Post post);
    public List<Post> showCategoryPost(@Param("category") String category);
    public List<Post> showPostByHeart(String category);
    public int showCategoryPostCount(@Param("category") String category);
    public List<Post> pagingBoard(@Param("category") String category,
                                  @Param("start") int start, @Param("offset") int offset);
    public int updateHit(Post post);
    public int downHit(Post post);
    public Integer updateHeartCountPlus(int pidx);
    public Integer updateHeartCountMinus(int pidx);
    public Integer showHeart(@Param("pidx") int pidx, @Param("idx") int idx);
    public int heartUp(HeartVO vo);
    public int heartDown(@Param("pidx") int pidx, @Param("idx") int idx);
    public List<Reply> showReply(int pidx);
    public int updateReplyCountPlus(int pidx);
    public int updateReplyCountMinus(int pidx);
    public Post postDetail(int pidx);
    public int updatePost(PostUpdateDto dto);
    public int deletePost(@Param("pidx") int pidx);
    public Integer searchPostsCount(String keyword);
    public Integer searchPostsCategoryCount(@Param("category") String category,
                                            @Param("keyword") String keyword);
    public List<Post> searchPostsByCategory(@Param("category") String category, @Param("keyword") String keyword,
                                            @Param("start") int start, @Param("offset") int offset);
    public List<Post> searchPostsAll(@Param("keyword") String keyword,
                                     @Param("start") int start, @Param("offset") int offset);

}
