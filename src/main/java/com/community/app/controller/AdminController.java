package com.community.app.controller;

import com.community.app.domain.Notice;
import com.community.app.dto.PagingVO;
import com.community.app.service.AdminService;
import com.community.app.service.BoardService;
import com.community.app.service.MemberService;
import com.community.app.service.ReplyService;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    BoardService boardService;

    @Autowired
    AdminService adminService;

    @Autowired
    ReplyService replyService;

    @Autowired
    MemberService memberService;

    ControllerMethods methods = new ControllerMethods();
    // 자주 사용하는 메서드(idx가져오기, 페이징) 담는 클래스

    // 전체 게시글 관리 - 페이지에 전체 게시글 보여주기
    @GetMapping("/admin/managePosts")
    public String managePosts(Model model,
                              @RequestParam(value="nowPage", required = false) String nowPage,
                              @RequestParam(value="cntPerPage", required = false) String cntPerPage) {

        int totalPost = adminService.adminShowPostsCount(); // 전체 게시글 개수
        PagingVO vo = methods.paging(totalPost, nowPage, cntPerPage); // 페이징 메서드

        model.addAttribute("data", adminService.adminShowPosts(vo.getStart(), vo.getOffset()));
        model.addAttribute("paging", vo);

        return "managePosts";
    }

    // 전체 게시글 관리 - 관리자 권한으로 게시글 삭제
    @GetMapping("/admin/managePosts/deletePost")
    public String adminDeletePost(int pidx) {
        boardService.deletePost(pidx); // 게시물 고유번호를 애용해 게시물 삭제
        return "redirect:/admin/managePosts";
    }

    // 전체 댓글 관리 - 페이지에 전체 댓글 보여주기
    @GetMapping("/admin/manageReplies")
    public String manageReplies(Model model,
                                @RequestParam(value = "nowPage", required = false) String nowPage,
                                @RequestParam(value = "cntPerPage", required = false) String cntPerPage) {

        int totalPost = adminService.adminShowPostsCount();
        PagingVO vo = methods.paging(totalPost, nowPage, cntPerPage);

        model.addAttribute("data", adminService.adminShowReply(vo.getStart(), vo.getOffset()));
        model.addAttribute("paging", vo);

        return "manageReplies";
    }

    // 전체 댓글 관리 - 관리자 권한으로 댓글 삭제
    @GetMapping("/admin/manageReplies/deleteReply")
    public String adminDeleteReply(int ridx) {
        int pidx = adminService.findPostFromRidx(ridx); // 삭제할 댓글번호로 찾은 댓글이 속한 게시글 번호
        boardService.updateReplyCountMinus(pidx);
        // post테이블의 replyCount(댓글갯수)를 -1 깎음

        replyService.deleteReply(ridx); // 댓글 삭제 메서드
        return "redirect:/admin/manageReplies";
    }

    // 전체 회원 관리 - 페이지에 전체 회원 보여주기
    @GetMapping("/admin/manageMembers")
    public String manageMembers(Model model,
                                @RequestParam(value = "nowPage", required = false) String nowPage,
                                @RequestParam(value = "cntPerPage", required = false) String cntPerPage) {

        int totalPost = adminService.adminShowMemberCount();
        PagingVO vo = methods.paging(totalPost, nowPage, cntPerPage);

        model.addAttribute("data", adminService.adminShowMember(vo.getStart(), vo.getOffset()));
        model.addAttribute("paging", vo);

        return "manageMembers";
    }

    // 전체 회원 관리 - 관리자 권한으로 회원 탈퇴
    @GetMapping("/admin/manageMembers/deleteMember")
    public String adminDeleteMember(int idx) {
        memberService.deleteMember(idx); // 회원 탈퇴 메서드

        return "redirect:/admin/manageMembers";
    }

    // 전체 공지 관리 - 페이지에 전체 공지 보여주기
    @GetMapping("/admin/manageNotices")
    public String manageNotices(Model model,
                                @RequestParam(value = "nowPage", required = false) String nowPage,
                                @RequestParam(value = "cntPerPage", required = false) String cntPerPage) {

        int totalPost = adminService.adminShowNoticeCount();
        PagingVO vo = methods.paging(totalPost, nowPage, cntPerPage);

        model.addAttribute("data", adminService.adminShowNotices(vo.getStart(), vo.getOffset()));
        model.addAttribute("paging", vo);

        return "manageNotices";
    }

    // 전체 공지 관리 - 공지 작성 페이지 보여주기
    @GetMapping("/admin/manageNotice/writeNotice")
    public String writeNotice() {
        return "writeNotice";
    }

    // 전체 공지 관리 - 공지 작성
    @PostMapping("/admin/manageNotice/writeNotice")
    public String writeNotice(Notice notice) {  
        String safeHtml = Jsoup.clean(notice.getContent(), Safelist.basic());
        notice.setContent(safeHtml);

        adminService.writeNotice(notice); // 공지 작성 메서드

        return "redirect:/admin/manageNotices";
    }

    // 공지 게시판 - 회원 읽기 가능
    @GetMapping("/notice")
    public String notice(Model model,
                         @RequestParam(value = "nowPage", required = false) String nowPage,
                         @RequestParam(value = "cntPerPage", required = false) String cntPerPage) {

        int totalPost = adminService.adminShowNoticeCount();
        PagingVO vo = methods.paging(totalPost, nowPage, cntPerPage);

        model.addAttribute("data", adminService.adminShowNotices(vo.getStart(), vo.getOffset()));
        model.addAttribute("paging", vo);

        return "notice";
    }

    // 공지 게시판 - 공지 상세보기
    @GetMapping("/noticeDetail")
    public String noticeDetail(int nidx, Notice notice,
                               Model model) {

        model.addAttribute("notice", adminService.showNoticeOne(nidx));

        return "noticeDetail";
    }

    // 전체 공지 관리 - 공지사항 수정 페이지
    @GetMapping("/admin/manageNotice/updateNotice")
    public String updateNoticePage(int nidx, Model model) {
        model.addAttribute("notice", adminService.showNoticeOne(nidx));

        return "updateNotice";
    }

    // 전체 공지 관리 - 공지사항 수정(post)
    @PostMapping("/admin/manageNotice/updateNotice")
    public String updateNotice(Notice notice) {
        adminService.adminUpdateNotice(notice);

        return "redirect:/noticeDetail?nidx="+notice.getNidx();
    }

    // 전체 공지 관리 - 공지사항 삭제
    @GetMapping("/admin/manageNotice/deleteNotice")
    public String deleteNotice(int nidx, HttpServletRequest request) {
        adminService.adminDeleteNotice(nidx);

        String referer = request.getHeader("Referer");

        return "redirect:"+referer;
    }

}
