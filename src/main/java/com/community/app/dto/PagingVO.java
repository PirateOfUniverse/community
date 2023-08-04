package com.community.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingVO {
    private int nowPage, startPage, endPage, total, cntPerPage, lastPage, start, end;
    private int cntPage = 5;


    public PagingVO() {}

    public PagingVO(int total, int nowPage, int cntPerPage) {
        setEnd(cntPerPage);
        setNowPage(nowPage);
        setCntPerPage(cntPerPage);
        setTotal(total);
        calcLastPage(getTotal(), getCntPerPage());
        calcStartEndPage(getNowPage(), cntPage);
        //setStart((nowPage-1) * cntPerPage);
        calcStartEnd(getNowPage(), getCntPerPage());

        // 게시물이 없을 때 nowPage 값이 0보다 작으면 1로 설정
        // 이것이 없으면 nowPage(현재 게시판 페이지)가 설정되지 않음
//        if (nowPage < 1 || lastPage == 0) {
//            setNowPage(1);
//        }

        // 데이터가 없는 경우에 대한 처리
        if (total == 0) {
            setNowPage(1);
            setStart(0);
            setEnd(0);
            setTotal(0);
            calcLastPage(0, cntPerPage);
            setStartPage(1);
            setEndPage(1);
            return;
        }

    }

    // 제일 마지막 페이지 계산
    public void calcLastPage(int total, int cntPerPage) {
        setLastPage((int) Math.ceil((double)total / (double)cntPerPage));
    }

    // 시작, 끝 페이지 계산
    public void calcStartEndPage(int nowPage, int cntPage) {
        setEndPage((int) (Math.ceil(nowPage / (double) cntPage) * cntPage));

        if(getLastPage() < nowPage) {
            setNowPage(getLastPage());
        }

        if (getLastPage() < getEndPage()) {
            setEndPage(getLastPage());
        }

        setStartPage((endPage - cntPage) + 1);

        // 시작 페이지가 0이 되지 않도록 수정
        // 이 내용이 없으면 마이너스 페이지가 나온다
        if (getStartPage() < 1) {
            setStartPage(1);
        }

        // 끝페이지가 0이거나, 현재 페이지와 값이 같으면(0) 마지막 페이지를 1로 설정
        // 이 코드가 없으면 게시물이 없는 경우 0페이지가 나온다..(오싹)
//        if(getEndPage() == 0 || getEndPage() == nowPage) {
//            setEndPage(1);
//        }

    }

     //DB 쿼리에서 사용할 start, end값 계산
    public void calcStartEnd(int nowPage, int cntPerPage) {
        setStart((nowPage-1) * cntPerPage);
    }

    @Override
    public String toString() {
        return "PagingVO [nowPage=" + nowPage + ", startPage=" + startPage + ", endPage=" + endPage + ", total=" + total
                + ", cntPerPage=" + cntPerPage + ", lastPage=" + lastPage + ", start=" + start + ", end=" + end
                + ", cntPage=" + cntPage + "]";
    }

}
