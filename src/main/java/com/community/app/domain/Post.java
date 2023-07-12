package com.community.app.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {

    private int pidx; // 게시물 번호
    private int idx; // 게시물 작성자 고유번호
    private String title; // 게시물 제목
    private String writer; // 게시물 작성자
    private String category; // 카테고리를 담는 변수
    private String content; // 게시물 내용
    private int replyCount; // 게시물 댓글 수
    private int hit; // 게시물 조회 수
    private int heartCount;

    private String regdate; // 게시물 작성일자

    public Post() {}

    public Post(int pidx, int idx, String title, String writer, String category,
                String content,int replyCount, int hit, int heartCount, String regdate) {
        this.pidx = pidx;
        this.idx = idx;
        this.title = title;
        this.writer = writer;
        this.category = category;
        this.content = content;
        this.replyCount = replyCount;
        this.hit = hit;
        this.heartCount = heartCount;
        this.regdate = regdate;
    }
}
