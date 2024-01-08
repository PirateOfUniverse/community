package com.community.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteSubDto {

    private int vsidx;
    private int vidx;
    private String answer;
    private int numberOfVotes;

    public VoteSubDto() {};

    public VoteSubDto(int vsidx, int vidx, String answer, int numberOfVotes) {
        this.vsidx = vsidx;
        this.vidx = vidx;
        this.answer = answer;
        this.numberOfVotes = numberOfVotes;
    }
}
