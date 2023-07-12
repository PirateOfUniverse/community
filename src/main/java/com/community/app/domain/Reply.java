package com.community.app.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reply {
    private int ridx;
    private int pidx;
    private int idx;
    private int reRidxNum;
    private int ridxDepth;
    private String replyWriter;
    private String replyContent;
    private String regdate;

    public Reply() {}

    public Reply(int ridx, int pidx, int idx, int reRidxNum, int ridxDepth,
                 String replyWriter, String replyContent, String regdate) {
        this.ridx = ridx;
        this.pidx = pidx;
        this.idx = idx;
        this.reRidxNum = reRidxNum;
        this.ridxDepth = ridxDepth;
        this.replyWriter = replyWriter;
        this.replyContent = replyContent;
        this.regdate = regdate;
    }
}
