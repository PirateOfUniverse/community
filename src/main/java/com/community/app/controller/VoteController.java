package com.community.app.controller;

import com.community.app.domain.Vote;
import com.community.app.dto.VoteSubDto;
import com.community.app.dto.VoterDto;
import com.community.app.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class VoteController {

    @Autowired
    VoteService voteService;

    // 투표하기
    @ResponseBody
    @PostMapping("/vote")
    public void votingVoter(@RequestBody VoterDto dto) {
        voteService.updateVoterVoteSub(dto.getVsidx(), dto.getVidx());
        voteService.updateVoterVote(dto.getVidx());
        System.out.println("vsidx: " + dto.getVsidx());
        voteService.votingVoter(dto);
    }



}
