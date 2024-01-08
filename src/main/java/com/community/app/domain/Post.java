package com.community.app.domain;

import lombok.*;

@Getter @Setter @EqualsAndHashCode(of = "pidx")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Post {
    // ctrl + shift + T = 새 테스트 생성 or 테스트코드로 이동

    // EqualsAndHashCode
    // Equals랑 hashcode사용할때 모든 필드를 기본적으로 사용
    // 나중에 객체간의 연관관계가 있을때, 상호참조관계가 되어버리면
    // 스택 오버플로우발생 가능성 있음.
    // pidx의 값만 가지고 equals, hashcode를 비교하게됨(다른필드로도 가능)
    // @Data도 스택 오버플로우 가능성이 있으므로 쓰지 않는것을 권장함

    private int pidx; // 게시물 번호
    private int idx; // 게시물 작성자 고유번호
    private String title; // 게시물 제목
    private String writer; // 게시물 작성자
    private String category; // 카테고리를 담는 변수
    private String content; // 게시물 내용
    private int replyCount; // 게시물 댓글 수
    private int hit; // 게시물 조회 수
    private int heartCount;
    private Integer postVote;
    private String regdate; // 게시물 작성일자

//    public Post() {}
//
//    public Post(int pidx, int idx, String title, String writer, String category,
//                String content,int replyCount, int hit, int heartCount,
//                Integer postVote,String regdate) {
//        this.pidx = pidx;
//        this.idx = idx;
//        this.title = title;
//        this.writer = writer;
//        this.category = category;
//        this.content = content;
//        this.replyCount = replyCount;
//        this.hit = hit;
//        this.heartCount = heartCount;
//        this.postVote = postVote;
//        this.regdate = regdate;
//    }
}
