package com.community.app.mapper;

import com.community.app.domain.Member;
import com.community.app.domain.Post;
import com.community.app.domain.Reply;
import com.community.app.dto.MemberUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper // 또는 @Repository
public interface MemberMapper {
    // 프로젝트별로 Mapper, Dao등 부르는 명칭은 많다

    public int insertMember(Member member);
    public int duplicateEmail(Member member);
    public int duplicateNick(Member member);
    public Member selectMemberOne(String email);
    public Member selectNick(String email);
    public Optional<Member> findProviderAndProviderId(@Param("provider") String provider,
                                                      @Param("providerId") String providerId);
    public String selectMemberSession(String nick);
    public String compareEmailAndPasswd(String email);
    public int selectMemberLoginCnt(Member member);
    public List<Post> showMyPosts(@Param("idx") int idx,
                                  @Param("start") int start, @Param("offset") int offset);
    public Integer showMyPostsCount(int idx);
    public List<Reply> showMyReplies(@Param("idx") int idx,
                                     @Param("start") int start, @Param("offset") int offset);
    public Integer showMyRepliesCount(int idx);
    public int updateMember(MemberUpdateDto dto);
    public int deleteMember(int idx);

}
