package com.community.app.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notice {
    private int nidx;
    private String title;
    private String writer;
    private String content;
    private String regdate;

    public Notice() {};

    public Notice(int nidx, String title, String writer, String content, String regdate) {
        this.nidx = nidx;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.regdate = regdate;
    }
}
