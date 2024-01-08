package com.community.app.controller;

import com.community.app.domain.Post;
import com.community.app.domain.Reply;
import com.community.app.domain.Vote;
import com.community.app.dto.HeartVO;
import com.community.app.dto.PagingVO;
import com.community.app.dto.PostUpdateDto;
import com.community.app.dto.VoteSubDto;
import com.community.app.service.BoardService;
import com.community.app.service.ReplyService;
import com.community.app.service.VoteService;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    ReplyService replyService;

    @Autowired
    VoteService voteService;

    ControllerMethods methods = new ControllerMethods();
    // 컨트롤러에서 공통적으로 쓰이는 메서드들의 클래스(페이징, 회원 고유번호(idx) 불러오기)

    /* 게시판 + 게시물 관련 기능 */

    // 게시물 작성 페이지로 이동
    @GetMapping("/write")
    public String insertBoard() {
        return "writePost";
    }

    // 게시물 작성 후 등록
    @PostMapping("/writePost")
    public String writePost(Post post, Vote vote, @RequestParam(value = "items[]") List<String> items) {
        String safeHtml = Jsoup.clean(post.getContent(), Safelist.basic());
        post.setContent(safeHtml);

        int makeVote = 0;
        int getVoteVidx = 0;

        // 게시글 작성할때 투표가 있으면 만들기
        if(!vote.getQuestion().equals("") && vote.getQuestion() != null) {
            int itemCount = items.size(); // item = 게시글의 항목 / itemCount = 투표항목 갯수
            vote.setItemCount(itemCount); // vote테이블에 itemCount컬럼의 값 넣기
            makeVote = voteService.makeVote(vote); // 변수 makeVote를 1로 만듦
            System.out.println("투표번호: " + vote.getVidx() + "투표제목: " + vote.getQuestion());
            System.out.println("투표항목 개수: " + items.size());
            //getVoteVidx = voteService.tempVidx(vote.getVidx());
            getVoteVidx = voteService.getVoteVidx(vote.getQuestion());
            // 1. 현재 매개변수 vote로 넘어온것중에 vidx는 없음(db에서 auto_increment되기 때문)
            // 2. 그래서 question값을 가져올수밖에 없었음.. 더 좋은방법 없으려나 ..

            post.setPostVote(getVoteVidx);
        }

        boardService.writePost(post);

        if(makeVote > 0) {
            // 투표항목생성 부분
            System.out.println("getVoteVidx: " + getVoteVidx);
            for(String item : items) {
                System.out.println("Items: " + items);

                VoteSubDto dto = new VoteSubDto();
                dto.setVidx(getVoteVidx);
                dto.setAnswer(item);
                voteService.makeVoteSub(dto);
            }
        }

        return "redirect:/board/"+post.getCategory();
    }

    // 카테고리별 게시판
    @GetMapping("/board/{category}")
    public String showCategoryPost(@PathVariable("category") String category,
                                   @RequestParam(value="nowPage", required = false) String nowPage,
                                   @RequestParam(value="cntPerPage",required = false) String cntPerPage,
                                   Model model) {

        int totalPost = boardService.showCategoryPostCount(category);
        PagingVO vo = methods.paging(totalPost, nowPage, cntPerPage);

        // db내 게시판 카테고리에 따라 게시판 이름을 지정하여 category라는 이름으로 model에 넣음
        // url에서 카테고리명을 빼서 db에서 검색을 하기 때문에 db내에는 카테고리가 영어로 적혀있음
        String boardCategory;
        if(category.equals("friendship")) {
            boardCategory = "친구/연애/가족고민";
        } else if(category.equals("life")) {
            boardCategory = "인생고민";
        } else if(category.equals("job")) {
            boardCategory = "취업/직장고민";
        } else if(category.equals("freeBoard")) {
            boardCategory = "자유게시판";
        } else {
            boardCategory = "가벼운고민";
        }
//        System.out.println(category);
//        System.out.println(boardCategory);

        model.addAttribute("engCategory", category);
        model.addAttribute("category", boardCategory);
        model.addAttribute("paging", vo);
        model.addAttribute("data", boardService.pagingBoard(category, vo.getStart(), vo.getEnd()));
        return "board";
    }

    // 게시판 카테고리 내 검색
    @GetMapping("/board/{category}/searchCategory")
    public String searchCategory(@PathVariable("category") String category, String keyword,
                                 @RequestParam(value="nowPage", required = false) String nowPage,
                                 @RequestParam(value="cntPerPage", required = false) String cntPerPage,
                                 Model model) {

        int totalPost = boardService.searchPostsCategoryCount(category, keyword);
        PagingVO vo = methods.paging(totalPost, nowPage, cntPerPage);

        String boardCategory;
        if(category.equals("friendship")) {
            boardCategory = "친구/연애/가족고민";
        } else if(category.equals("life")) {
            boardCategory = "인생고민";
        } else if(category.equals("job")) {
            boardCategory = "취업/직장고민";
        } else if(category.equals("freeBoard")) {
            boardCategory = "자유게시판";
        } else {
            boardCategory = "가벼운고민";
        }
//        System.out.println(category);
//        System.out.println(boardCategory);

        model.addAttribute("category", boardCategory);
        model.addAttribute("paging", vo);
        model.addAttribute("data", boardService.searchPostsByCategory(category, keyword, vo.getStart(), vo.getEnd()));

        return "board";
    }

    // 게시물 상세 보기(내용, 댓글, 추천)
    @GetMapping("/postDetail")
    public String postDetail(int pidx, Post post,
                             Model model,
                             Authentication authentication) {
        String path = "";
        HeartVO vo = new HeartVO();

        if(authentication.isAuthenticated()) {
            model.addAttribute("post", boardService.postDetail(pidx));
            model.addAttribute("reply", boardService.showReply(pidx));

            // 사용자 정보(idx)가져오기
            int idx = methods.getMemberIdx(authentication);

            Integer showHeart = boardService.showHeart(pidx, idx);
            if(showHeart == null) {
                model.addAttribute("heart", 0);
            } else {
                model.addAttribute("heart", showHeart);
            }

            path = "postDetail";
            boardService.updateHit(post);
        }
        // vote가져오기(없으면 null)
        Vote voteInPost = voteService.getVidxFromPost(pidx);
        int vidx = 0;

        if(voteInPost != null) {
            // null이 아님 = 투표가 있음
            // 투표 고유 번호 가져오기
            // if문 없으면 중간에 오류 발생(NullPointerException)
            vidx = voteInPost.getVidx();
        }

        // 투표를 첨부했다면 가져오기
        if(voteInPost != null) {
            System.out.println("안에 투표가 있어요");
            model.addAttribute("vote", voteInPost);
            model.addAttribute("voteSub", voteService.getVoteSubList(vidx));
            // vidx로 투표항목 가져와서 집어넣기

            // 사용자가 이 투표에 투표를 했느냐 안했느냐 확인
            Integer showVoteOrNot = voteService.showVoteOrNot(vidx, methods.getMemberIdx(authentication));
            Integer whatVoterVote = voteService.whatVoterVote(methods.getMemberIdx(authentication), vidx);
            if(showVoteOrNot == null) {
                model.addAttribute("voteCheck", 0);
            } else {
                model.addAttribute("voteCheck", showVoteOrNot);
                model.addAttribute("votedSub", whatVoterVote);
            }
        }

        return path;
    }

    // 게시물 수정 페이지 이동
    @GetMapping("/updatePost")
    public String update(int pidx, Model model) {
        model.addAttribute("post", boardService.postDetail(pidx));

        //Integer getPostVote = voteService.getVidxFromPost(pidx);
        Vote voteInPost = voteService.getVidxFromPost(pidx);
        int vidx = voteInPost.getVidx();

        if(voteInPost != null) {
            model.addAttribute("vote", voteInPost);
            model.addAttribute("voteSub", voteService.getVoteSubList(vidx));
        }

        return "updatePost";
    }

    // 게시물 수정
    @PostMapping("/updatePost")
    public String updatePost(PostUpdateDto dto) {
        boardService.updatePost(dto);

        return "redirect:/postDetail?pidx="+dto.getPidx();
        // 카테고리 받아와서 해당 게시판으로 이동
    }

    // 게시물 삭제
    @ResponseBody
    @PostMapping("/deletePost")
    public void deletePost(@RequestParam(value="pidx", required = false) int pidx,
                             @RequestParam(value="category", required = false) String category) {
        boardService.deletePost(pidx);

        //return "redirect:/board/"+category;
    }

    /* 댓글 관련 기능 */

    // 댓글 작성
    @PostMapping("/writeReply")
    public String writeReply(Post post, Reply reply, HttpServletRequest request) {
        replyService.writeReply(reply);
        boardService.updateReplyCountPlus(reply.getPidx());
        boardService.downHit(post);
        String referer = request.getHeader("referer");

        return "redirect:"+referer;
    }

    // 댓글 수정
    @PostMapping("/updateReply")
    public String updateReply(Post post, Reply reply, HttpServletRequest request) {
        replyService.updateReply(reply);
        boardService.downHit(post);
        String referer = request.getHeader("Referer");

        return "redirect:"+referer;
    } 

    // 댓글 삭제
    @GetMapping("/deleteReply")
    public String deleteReply(@RequestParam("ridx") int ridx, Post post,
                                HttpServletRequest request) {

        boardService.updateReplyCountMinus(replyService.selectReplyPidx(ridx));
        replyService.deleteReply(ridx);

        boardService.downHit(post);
        String referer = request.getHeader("Referer"); // 이전페이지로 이동

        return "redirect:"+referer;
    }

    /* 대댓글 관련 기능 */
    
    // 대댓글 작성
    @PostMapping("/writeReReply")
    public String writeReReply(Post post, Reply reply, HttpServletRequest request) {
        replyService.writeReReply(reply);
        boardService.updateReplyCountPlus(reply.getPidx());
        boardService.downHit(post);
        String referer = request.getHeader("referer");

        return "redirect:"+referer;
    }


    /* 게시물 추천 관련 기능 */
    
    // 게시물 추천
    @ResponseBody
    @PostMapping("/heartUp")
    public void heartUp(@RequestBody HeartVO vo) {
        boardService.updateHeartCountPlus(vo.getPidx());
        boardService.heartUp(vo);
    }

    // 게시물 추천 취소
    @ResponseBody
    @PostMapping("/heartDown")
    public void heartDown(@RequestBody HeartVO vo) {
        boardService.updateHeartCountMinus(vo.getPidx());
        boardService.heartDown(vo.getPidx(), vo.getIdx());
    }

    // 게시물 검색 - 전체 카테고리
    @GetMapping("/search")
    public String searchPosts(String keyword,
                              @RequestParam(value="nowPage", required = false) String nowPage,
                              @RequestParam(value="cntPerPage", required = false) String cntPerPage,
                              Model model) {

        int totalPost = boardService.searchPostsCount(keyword);
        PagingVO vo = methods.paging(totalPost, nowPage, cntPerPage);

        model.addAttribute("keyword", keyword);
        model.addAttribute("paging", vo);
        model.addAttribute("data", boardService.searchPostsAll(keyword, vo.getStart(), vo.getEnd()));

        return "searchPosts";
    }

}
