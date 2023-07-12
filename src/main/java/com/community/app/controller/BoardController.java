package com.community.app.controller;

import com.community.app.domain.Post;
import com.community.app.domain.Reply;
import com.community.app.dto.HeartVO;
import com.community.app.dto.PagingVO;
import com.community.app.dto.PostUpdateDto;
import com.community.app.service.BoardService;
import com.community.app.service.ReplyService;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    ReplyService replyService;

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
    public String writePost(Post post) {
        String safeHtml = Jsoup.clean(post.getContent(), Safelist.basic());
        post.setContent(safeHtml);

        boardService.writePost(post);

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
        if(category.equals("counseling")) {
            boardCategory = "고민상담게시판";
        } else if(category.equals("hobby")) {
            boardCategory = "취미게시판";
        } else if(category.equals("ent")) {
            boardCategory = "연예게시판";
        } else if(category.equals("freeBoard")) {
            boardCategory = "자유게시판";
        } else {
            boardCategory = "Daily";
        }
//        System.out.println(category);
//        System.out.println(boardCategory);

        model.addAttribute("category", boardCategory);
        model.addAttribute("paging", vo);
        model.addAttribute("data", boardService.pagingBoard(category, vo.getStart(), vo.getOffset()));
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
        if(category.equals("counseling")) {
            boardCategory = "고민상담게시판";
        } else if(category.equals("hobby")) {
            boardCategory = "취미게시판";
        } else if(category.equals("ent")) {
            boardCategory = "연예게시판";
        } else if(category.equals("freeBoard")) {
            boardCategory = "자유게시판";
        } else {
            boardCategory = "Daily";
        }
//        System.out.println(category);
//        System.out.println(boardCategory);

        model.addAttribute("category", boardCategory);
        model.addAttribute("paging", vo);
        model.addAttribute("data", boardService.searchPostsByCategory(category, keyword, vo.getStart(), vo.getOffset()));

        return "board";
    }

    // 게시물 상세 보기(내용, 댓글, 추천)
    @GetMapping("/postDetail")
    public String postDetail(int pidx, Post post,
                             Model model,
                             Authentication authentication) {
        String path = "";
        HeartVO vo = new HeartVO();

        if(!authentication.isAuthenticated()) {
            path = "notMember";
        } if(authentication.isAuthenticated()) {
            model.addAttribute("post", boardService.postDetail(pidx));
            model.addAttribute("reply", boardService.showReply(pidx));

            //int memberIdx = 0; // 초기화
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

        return path;
    }

    // 게시물 수정 페이지 이동
    @GetMapping("/updatePost")
    public String update(int pidx, Model model) {
        model.addAttribute("post", boardService.postDetail(pidx));

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
    @GetMapping("/deletePost")
    public String deletePost(@RequestParam(value="pidx", required = false) int pidx,
                             @RequestParam(value="category", required = false) String category) {
        boardService.deletePost(pidx);

        return "redirect:/board/"+category;
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

        model.addAttribute("paging", vo);
        model.addAttribute("data", boardService.searchPostsAll(keyword, vo.getStart(), vo.getOffset()));

        return "searchPosts";
    }

}
