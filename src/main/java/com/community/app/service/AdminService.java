package com.community.app.service;

import com.community.app.domain.Member;
import com.community.app.domain.Notice;
import com.community.app.domain.Post;
import com.community.app.domain.Reply;
import com.community.app.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements AdminMapper {

    @Autowired
    AdminMapper mapper;

    @Override
    public List<Post> adminShowPosts(int start, int offset) {
        return mapper.adminShowPosts(start, offset);
    }

    @Override
    public Integer adminShowPostsCount() {
        return mapper.adminShowPostsCount();
    }

    @Override
    public List<Reply> adminShowReply(int start, int offset) {
        return mapper.adminShowReply(start, offset);
    }

    @Override
    public Integer adminShowReplyCount() {
        return mapper.adminShowReplyCount();
    }

    @Override
    public int findPostFromRidx(int ridx) {
        return mapper.findPostFromRidx(ridx);
    }

    @Override
    public List<Member> adminShowMember(int start, int offset) {
        return mapper.adminShowMember(start, offset);
    }

    @Override
    public Integer adminShowMemberCount() {
        return mapper.adminShowMemberCount();
    }

    @Override
    public int writeNotice(Notice notice) {
        return mapper.writeNotice(notice);
    }

    @Override
    public int adminShowNoticeCount() {
        return mapper.adminShowNoticeCount();
    }

    @Override
    public List<Notice> adminShowNotices(int start, int offset) {
        return mapper.adminShowNotices(start, offset);
    }

    @Override
    public Notice showNoticeOne(int nidx) {
        return mapper.showNoticeOne(nidx);
    }

    @Override
    public int adminUpdateNotice(Notice notice) {
        return mapper.adminUpdateNotice(notice);
    }

    @Override
    public int adminDeleteNotice(int nidx) {
        return mapper.adminDeleteNotice(nidx);
    }


}
