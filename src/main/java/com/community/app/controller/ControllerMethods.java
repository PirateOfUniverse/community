package com.community.app.controller;

import com.community.app.dto.PagingVO;
import com.community.app.security.UserCustom;
import com.community.app.security.oauth.PrincipalDetails;
import org.springframework.security.core.Authentication;

public class ControllerMethods {

    public PagingVO paging(int total, String nowPage, String cntPerPage) {
        cntPerPage = cntPerPage != null ? cntPerPage : "10";
        // cntPerPage가 null인 경우 기본값 10으로 설정

        if(nowPage == null) {
            // 게시판에 처음들어갔을때 나오는 페이지(1번으로 설정)
            nowPage = "1";
        }
        PagingVO vo = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));

        return vo;
    }

    public int getMemberIdx(Authentication authentication) {
        int idx;
        Object principal = authentication.getPrincipal();
        // 일반로그인과 OAuth2로그인 두종류가 있기 때문에 principal을 Object타입으로 받아서
        // 어떠한 principal을 받아오든 담을 수 있도록 설정
        if(principal instanceof UserCustom) {
            // 일반로그인
            idx = ((UserCustom) principal).getIdx();
        } else if(principal instanceof PrincipalDetails) {
            // OAuth2로그인
            idx = ((PrincipalDetails) principal).getIdx();
        } else {
            // principal type이 UserCustom, PrincipalDetails중에 없는경우
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass());
        }

        return idx;
    }

}
