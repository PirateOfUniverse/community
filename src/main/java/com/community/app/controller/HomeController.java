package com.community.app.controller;

import com.community.app.security.UserCustom;
import com.community.app.service.BoardService;
import com.community.app.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @Autowired
    BoardService boardService;
    @Autowired
    MemberService memberService;

    // 메인화면
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("freeBoard", boardService.showPostByHeart("freeBoard"));
        model.addAttribute("ent", boardService.showPostByHeart("ent"));
        model.addAttribute("daily", boardService.showPostByHeart("daily"));
        model.addAttribute("hobby", boardService.showPostByHeart("hobby"));

        return "main";
    }

    /* 테스트 */
//    @GetMapping("/manager")
//    @ResponseBody
//    public String manager() {
//        return "manager";
//    }
//
//    @GetMapping("/admin")
//    @Secured("ROLE_ADMIN")
//    @ResponseBody
//    public String admin() {
//        return "어드민 페이지 입니다";
//    }
//
//    @GetMapping("/a")
//    @ResponseBody
//    public void a(Authentication auth) {
//        System.out.println(auth.getAuthorities());
//    }
//
//    @GetMapping("/info")
//    public @ResponseBody String info() {
//        return "개인정보";
//    }
//
//    @RequestMapping("/test")
//    @ResponseBody
//    public String test2(@AuthenticationPrincipal UserCustom user) {
//        System.out.println("===============" + user);
//        return "test";
//    }

}
