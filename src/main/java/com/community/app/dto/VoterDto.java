package com.community.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoterDto {

    private int voteridx;
    private int vidx;
    private int vsidx;
    private int idx;
    private String regdate;

    public VoterDto() {};

    public VoterDto(int voteridx, int vidx, int vsidx, int idx, String regdate) {
        this.voteridx = voteridx;
        this.vidx = vidx;
        this.vsidx = vsidx;
        this.idx = idx;
        this.regdate = regdate;
    }
}
