package com.community.app;


import com.community.app.domain.Vote;
import com.community.app.mapper.BoardMapper;
import com.community.app.service.BoardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardServiceTest {

    BoardService boardService;

    @BeforeEach
    public void beforeEach() {
        boardService = new BoardService();
    }

    @Test
    void 투표만들기() {
        // given
        Vote vote = new Vote();
        vote.setQuestion("투표 테스트여요");

        // when
        // 아 씨발!

    }


}
