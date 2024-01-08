package com.community.app.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vote {

    private int vidx;
    private String question;
    private int itemCount;
    private int voteTotal;
    private String regdate;

    public Vote() {};

    public Vote(int vidx, String question, int itemCount, int voteTotal, String regdate) {
        this.vidx = vidx;
        this.question = question;
        this.itemCount = itemCount;
        this.voteTotal = voteTotal;
        this.regdate = regdate;
    }
}
