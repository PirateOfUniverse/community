package com.community.app.service;

import com.community.app.domain.Notice;
import com.community.app.domain.Post;
import com.community.app.domain.Reply;
import com.community.app.dto.HeartVO;
import com.community.app.dto.PagingVO;
import com.community.app.dto.PostUpdateDto;
import com.community.app.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService implements BoardMapper {

    @Autowired
    private BoardMapper mapper;


    @Override
    public int writePost(Post post) {
        return mapper.writePost(post);
    }

    @Override
    public List<Post> showCategoryPost(String category) {
        return mapper.showCategoryPost(category);
    }

    @Override
    public List<Post> showPostByHeart(String category) {
        return mapper.showPostByHeart(category);
    }

    @Override
    public int showCategoryPostCount(String category) {
        return mapper.showCategoryPostCount(category);
    }

    @Override
    public List<Post> pagingBoard(String category, int start, int offset) {
        return mapper.pagingBoard(category, start, offset);
    }

    @Override
    public int updateHit(Post post) {
        return mapper.updateHit(post);
    }

    @Override
    public int downHit(Post post) {
        return mapper.downHit(post);
    }

    @Override
    public Integer updateHeartCountPlus(int pidx) {
        return mapper.updateHeartCountPlus(pidx);
    }

    @Override
    public Integer updateHeartCountMinus(int pidx) {
        return mapper.updateHeartCountMinus(pidx);
    }

    @Override
    public Integer showHeart(int pidx, int idx) {
        return mapper.showHeart(pidx, idx);
    }

    @Override
    public int heartUp(HeartVO vo) {
        return mapper.heartUp(vo);
    }

    @Override
    public int heartDown(int pidx, int idx) {
        return mapper.heartDown(pidx, idx);
    }

    @Override
    public List<Reply> showReply(int pidx) {
        return mapper.showReply(pidx);
    }

    @Override
    public int updateReplyCountPlus(int pidx) {
        return mapper.updateReplyCountPlus(pidx);
    }

    @Override
    public int updateReplyCountMinus(int pidx) {
        return mapper.updateReplyCountMinus(pidx);
    }

    @Override
    public Post postDetail(int pidx) {
        return mapper.postDetail(pidx);
    }

    @Override
    public int updatePost(PostUpdateDto dto) {
        return mapper.updatePost(dto);
    }

    @Override
    public int deletePost(int pidx) {
        return mapper.deletePost(pidx);
    }

    @Override
    public Integer searchPostsCount(String keyword) {
        return mapper.searchPostsCount(keyword);
    }

    @Override
    public Integer searchPostsCategoryCount(String category, String keyword) {
        return mapper.searchPostsCategoryCount(category, keyword);
    }
    @Override
    public List<Post> searchPostsByCategory(String category, String keyword, int start, int offset) {
        return mapper.searchPostsByCategory(category, keyword, start, offset);
    }
    @Override
    public List<Post> searchPostsAll(String keyword, int start, int offset) {
        return mapper.searchPostsAll(keyword, start, offset);
    }

    @Override
    public int getVoteInPost(int pidx) {
        return mapper.getVoteInPost(pidx);
    }

}
