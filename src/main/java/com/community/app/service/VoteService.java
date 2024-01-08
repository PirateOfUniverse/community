package com.community.app.service;

import com.community.app.domain.Vote;
import com.community.app.dto.VoteSubDto;
import com.community.app.dto.VoterDto;
import com.community.app.mapper.VoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoteService implements VoteMapper {

    @Autowired
    private VoteMapper mapper;


    @Override
    public List<Vote> getVoteList() {
        return mapper.getVoteList();
    }

//    @Override
//    public Integer getVidxFromPost(int pidx) {
//        return mapper.getVidxFromPost(pidx);
//    }
//
//    @Override
//    public Vote getVote(int vidx) {
//        return mapper.getVote(vidx);
//    }

    @Override
    public Vote getVidxFromPost(int pidx) {
        return mapper.getVidxFromPost(pidx);
    }

    @Override
    public List<VoteSubDto> getVoteSubList(int vidx) {
        return mapper.getVoteSubList(vidx);
    }

    @Override
    public Integer showVoteOrNot(int vidx, int idx) {
        return mapper.showVoteOrNot(vidx, idx);
    }

    @Override
    public int makeVote(Vote vote) {
        return mapper.makeVote(vote);
    }

    @Override
    public Integer getVoteVidx(String question) {
        return mapper.getVoteVidx(question);
    }

    @Override
    public Integer tempVidx(int vidx) {
        return mapper.tempVidx(vidx);
    }

    @Override
    public int makeVoteSub(VoteSubDto dto) {
        return mapper.makeVoteSub(dto);
    }

    @Override
    public Integer whatVoterVote(int idx, int vidx) {
        return mapper.whatVoterVote(idx, vidx);
    }
    @Override
    public int votingVoter(VoterDto dto) {
        return mapper.votingVoter(dto);
    }

    @Override
    public int updateVoterVote(int vidx) {
        return mapper.updateVoterVote(vidx);
    }

    @Override
    public int updateVoterVoteSub(int vsidx, int vidx) {
        return mapper.updateVoterVoteSub(vsidx, vidx);
    }
}
