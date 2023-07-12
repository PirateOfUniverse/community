package com.community.app.mapper;

import com.community.app.domain.Member;
import com.community.app.domain.Notice;
import com.community.app.domain.Post;
import com.community.app.domain.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    public List<Post> adminShowPosts(@Param("start") int start,
                                     @Param("offset") int offset);
    public Integer adminShowPostsCount();
    public List<Reply> adminShowReply(@Param("start") int start,
                                      @Param("offset") int offset);
    public Integer adminShowReplyCount();
    public int findPostFromRidx(int ridx);
    public List<Member> adminShowMember(@Param("start") int start,
                                        @Param("offset") int offset);
    public Integer adminShowMemberCount();
    public int writeNotice(Notice notice);
    public int adminShowNoticeCount();
    public List<Notice> adminShowNotices(@Param("start") int start, @Param("offset") int offset);
    public Notice showNoticeOne(int nidx);
    public int adminUpdateNotice(Notice notice);
    public int adminDeleteNotice(int nidx);

}
