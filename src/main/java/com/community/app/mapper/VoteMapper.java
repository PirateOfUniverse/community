package com.community.app.mapper;

import com.community.app.domain.Vote;
import com.community.app.dto.VoteSubDto;
import com.community.app.dto.VoterDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VoteMapper {

    public List<Vote> getVoteList();
//    public Integer getVidxFromPost(int pidx);
//    public Vote getVote(int vidx);
    public Vote getVidxFromPost(int pidx);
    public List<VoteSubDto> getVoteSubList(int vidx);
    public Integer showVoteOrNot(@Param("vidx")int vidx, @Param("idx")int idx);
    public int makeVote(Vote vote);
    public Integer getVoteVidx(String question);
    public Integer tempVidx(int vidx);
    public int makeVoteSub(VoteSubDto dto);
    public Integer whatVoterVote(@Param("idx") int idx, @Param("vidx")int vidx);
    public int votingVoter(VoterDto dto);
    public int updateVoterVote(int vidx);
    public int updateVoterVoteSub(@Param("vsidx") int vsidx, @Param("vidx") int vidx);

}
