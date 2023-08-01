package com.community.app.service;

import com.community.app.domain.Member;
import com.community.app.domain.Post;
import com.community.app.domain.Reply;
import com.community.app.dto.MemberUpdateDto;
import com.community.app.mapper.MemberMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements MemberMapper{

    @Autowired
    private MemberMapper mapper;

    @Override
    public int insertMember(Member member) {
        return mapper.insertMember(member);
    }

    @Override
    public int duplicateEmail(Member member) {
        return mapper.duplicateEmail(member);
    }

    @Override
    public int duplicateNick(Member member) {
        return mapper.duplicateNick(member);
    }

    @Override
    public Member selectMemberOne(String email) {
        return mapper.selectMemberOne(email);
    }

    @Override
    public Member selectNick(String email) {
        return mapper.selectNick(email);
    }

    @Override
    public Optional<Member> findProviderAndProviderId(String provider, String providerId) {
        return mapper.findProviderAndProviderId(provider, providerId);
    }

    @Override
    public String selectMemberSession(String nick) {
        return mapper.selectMemberSession(nick);
    }
    @Override
    public String compareEmailAndPasswd(String email) {
        return mapper.compareEmailAndPasswd(email);
    }

    @Override
    public int selectMemberLoginCnt(Member member) {
        return mapper.selectMemberLoginCnt(member);
    } // 로그인 성공 실패유무 가릴때 사용

    @Override
    public List<Post> showMyPosts(int idx, int start, int offset) {
        return mapper.showMyPosts(idx, start, offset);
    }

    @Override
    public Integer showMyPostsCount(int idx) {
        return mapper.showMyPostsCount(idx);
    }

    @Override
    public List<Reply> showMyReplies(int idx, int start, int offset) {
        return mapper.showMyReplies(idx, start, offset);
    }

    @Override
    public Integer showMyRepliesCount(int idx) {
        return mapper.showMyRepliesCount(idx);
    }

    @Override
    public int updateMember(MemberUpdateDto dto) {
        return mapper.updateMember(dto);
    }

    @Override
    public int deleteMember(int idx) {
        return mapper.deleteMember(idx);
    }
}
