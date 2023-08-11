package com.community.app.controller;

import com.community.app.domain.Member;
import com.community.app.dto.MemberUpdateDto;
import com.community.app.dto.PagingVO;
import com.community.app.security.oauth.PrincipalDetails;
import com.community.app.security.UserCustom;
import com.community.app.service.MailService;
import com.community.app.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    MailService mailService;

    ControllerMethods methods = new ControllerMethods();
    // 컨트롤러에서 공통적으로 쓰이는 메서드들의 클래스(페이징, 회원 고유번호(idx) 불러오기)

    @Autowired
    private BCryptPasswordEncoder encoder;

    /* 회원가입 기능 */

    // 회원가입 페이지로 이동
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // 회원가입 - 일반 회원 가입 페이지(OAuth2 X)
    @PostMapping("/register")
    public String memberRegister(Member member) {
        String rawPasswd = member.getPasswd();
        String encodedPasswd = encoder.encode(rawPasswd);
        member.setPasswd(encodedPasswd);
        memberService.insertMember(member);
        return "redirect:/login";
    }

    // 회원가입 - 이메일 중복체크
    @ResponseBody
    @GetMapping("/email-check")
    public int duplicateEmail(Member member) {
        int result = memberService.duplicateEmail(member);
        return result;
    }

    // 회원가입 - 메일로 랜덤 코드 전송
    @ResponseBody
    @PostMapping("/mail-confirm")
    public String mailConfirm(@RequestParam("email") String email) throws Exception {
        String randomCode = mailService.sendSimpleMessage(email);
        System.out.println(randomCode);

        return randomCode;
    }

    // 회원가입 - 닉네임 중복체크
    @ResponseBody
    @GetMapping("/nick-check")
    public int duplicateNick(Member member) {
        int result = memberService.duplicateNick(member);
        return result;
    }

    /* 로그인 기능 */

    // 로그인 - 로그인 창으로 이동
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    // 로그인 - 시큐리티 사용으로 폐기
//    @ResponseBody
//    @PostMapping("/login")
//    public String memberLogin(@RequestParam("email") String email,
//                              @RequestParam("passwd") String passwd,
//                              HttpServletRequest request, Model model,
//                              Authentication auth) {
//        String message = "";
//        session = request.getSession();
//        session.setAttribute("memberInfo", auth);
//
//
//        if(session == null) {
//            System.out.println("세션없음");
//        }
//
//        /*// 이메일, 패스워드 검증 로직
//        String findEncodedPasswd = memberService.compareEmailAndPasswd(email);
//        boolean match = encoder.matches(passwd, findEncodedPasswd);
//
//        if(match == true) {
//            Member member = memberService.selectMemberOne(email);
//            String memberNick = member.getNick();
//            session.setAttribute("memberInfo", member);
//            message = "<script>alert('로그인되었습니다');" +
//                    "location.href='/';</script>";
//
//            System.out.println("성공");
//            System.out.println(member.getIdx());
//
//        } else {
//            model.addAttribute("message", "fail");
//            //path = "redirect:/login"; // 추후 수정필요(팝업뜬다던가)
//            message = "<script>alert('로그인에 실패하였습니다');" +
//                    "location.href='login';</script>";
//            System.out.println("실패ㅠ");
//        }*/
//
//        return "redirect:/";
//    }

    // 로그아웃 - 시큐리티 사용으로 폐기
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request) {
//        session = request.getSession(false);
//        session.invalidate();
//        System.out.println("로그아웃성공");
//
//        return "redirect:/";
//    }

    /* 마이페이지 메뉴들 */

    // 작성 게시글 보기 - 내가 쓴 게시물 전체보기
    @GetMapping("/myPage/myPosts")
    public String myPosts(Model model, Authentication authentication,
                          @RequestParam(value="nowPage", required = false) String nowPage,
                          @RequestParam(value="cntPerPage", required = false) String cntPerPage
    ) {
        //int memberIdx = 0; // 초기화
        int idx = methods.getMemberIdx(authentication);

        int total = memberService.showMyPostsCount(idx);
        PagingVO vo = methods.paging(total, nowPage, cntPerPage);

        model.addAttribute("data", memberService.showMyPosts(idx, vo.getStart(), vo.getEnd()));
        model.addAttribute("paging", vo);

        return "myPosts";
    }
    
    // 작성 댓글 보기 - 내가 쓴 댓글 전체보기
    @GetMapping("/myPage/myReplies")
    public String myReplies(Model model, Authentication authentication,
                            @RequestParam(value="nowPage", required = false) String nowPage,
                            @RequestParam(value="cntPerPage", required = false) String cntPerPage
    ) {
        int idx = methods.getMemberIdx(authentication); // 회원 고유번호 가져오기

        int total = memberService.showMyRepliesCount(idx); // 고유번호로 작성한 댓글 수 카운트
        PagingVO vo = methods.paging(total, nowPage, cntPerPage);
        // 카운트한 댓글 수와 현재페이지, 한페이지당 댓글 개수를 매개변수로 페이징

        model.addAttribute("data", memberService.showMyReplies(idx, vo.getStart(), vo.getEnd()));
        // 위에서 받아온 정보들(고유번호, 페이징vo의 정보)을 바탕으로 댓글을 보여주기위해 모델에 data란 이름으로 담음
        model.addAttribute("paging", vo);
        // 페이징용 모델

        return "myReplies";
    }
    
    // 회원정보 수정 - 사용자 정보 불러오기
    @GetMapping("/myPage/updateMember")
    public String updateMember(Model model, Authentication authentication) {
        String email = ""; // email 초기화

        Object principal = authentication.getPrincipal(); 
        // principal을 받아 저장할 변수. 모든 타입을 받을 수 있도록 Optional 사용
        if(principal instanceof UserCustom) {
            email = ((UserCustom) principal).getEmail(); // 일반 로그인 회원 정보 받아오기
        } else if(principal instanceof PrincipalDetails) {
            email = ((PrincipalDetails) principal).getEmail(); // OAuth2 로그인 회원정보 받아오기
        } else {
            throw new IllegalStateException("Unexpected principal type: " +principal.getClass());
            // 예외처리
        }

        model.addAttribute("member", memberService.selectMemberOne(email));
        // 변수 email에 담긴 정보를 매개로 회원 정보 가져오기
        return "updateMember";
    }

    // 회원정보 수정 - 비밀번호 변경을 위한 코드 받기
    @ResponseBody
    @PostMapping("/getPasswdCode")
    public String pwMailConfirm(@RequestParam("email") String email) throws Exception {
        String randomCode = mailService.sendPasswdMessage(email);
        // 파라미터로 받아온 email로 비밀번호 변경을 위한 코드 전송(자바스크립트)
        System.out.println(randomCode);

        return randomCode;
    }

    // 회원정보 수정 - Post 수정 작업
//    @PostMapping("/myPage/updateMember")
//    public String updateMember(MemberUpdateDto dto, Authentication authentication) {
//        if(dto.getPasswd() != "" && dto.getPasswd() != null) {
//            // 비밀번호 변경이 이루어졌을 경우
//            // BCryptPasswordEncoder로 비밀번호 인코딩 시켜서 보안성 강화
//            dto.setPasswd(encoder.encode(dto.getPasswd()));
//        }
//        memberService.updateMember(dto); // db내부의 member테이블에 회원정보 업데이트
//
//        // 현재 인증된 사용자의 세션 정보 가져오기
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof UserCustom) {
//            UserCustom userCustom = (UserCustom) principal;
//            userCustom.setUsername(dto.getNick()); // 세션 정보(닉네임) 변경하기
//        } else if(principal instanceof PrincipalDetails) {
//            PrincipalDetails principalDetails = (PrincipalDetails) principal;
//            principalDetails.setUsername(dto.getNick()); // 세션 정보(닉네임) 변경하기
//        }
//
//        return "redirect:/";
//    }

    @PostMapping("/myPage/updateMember")
    public String updateMember(MemberUpdateDto dto, Authentication authentication) {

        // dto가 null인지 확인
        if (dto == null) {
            // 에러 처리 (예: 로그 출력)
            return "error"; // 또는 적절한 오류 페이지로 이동
        }

        // 비밀번호와 닉네임 정보가 제대로 전달되는지 확인
        System.out.println("Passwd: " + dto.getPasswd());
        System.out.println("Nick: " + dto.getNick());

        /////////////////////////////////////////////////////////////////
        if (dto.getPasswd() != null && !dto.getPasswd().isEmpty()) {
            dto.setPasswd(encoder.encode(dto.getPasswd()));
        }

        memberService.updateMember(dto);

        Object principal = authentication.getPrincipal();
        if(principal instanceof UserCustom) {
            UserCustom userCustom = (UserCustom) principal;
            userCustom.setUsername(dto.getNick());
        } else if(principal instanceof PrincipalDetails) {
            PrincipalDetails principalDetails = (PrincipalDetails) principal;
            principalDetails.setUsername(dto.getNick());
        }

        return "redirect:/";
    }

    // 회원정보 수정 - 회원탈퇴
    @ResponseBody
    @PostMapping("/deleteMember")
    public void deleteMember(@RequestParam(value="idx") int idx) {
        memberService.deleteMember(idx);
        // 회원탈퇴 시 db에서 회원정보가 idx기준으로 삭제됨
    }


    // 회원가입 인증 메일 https://badstorage.tistory.com/38


    /*@GetMapping("/password-test")
    public void passwdTest() {

        String rawPasswd = "12345";
        String encodePasswd1 = encoder.encode(rawPasswd);
        String encodePasswd2 = encoder.encode(rawPasswd);

        System.out.println(encodePasswd1);
        System.out.println(encodePasswd2);

        String rightPasswd = "12345";
        String wrongPasswd = "abcde";

        System.out.println("Password verify: " + encoder.matches(rightPasswd, encodePasswd1));
        System.out.println("Password verify: " + encoder.matches(wrongPasswd, encodePasswd1));


    }*/

    /* 세션 정보 테스트코드 */
//    @GetMapping("/test/login")
//    public @ResponseBody String testLogin(Authentication auth, @AuthenticationPrincipal PrincipalDetails pd) {
//        System.out.println("/test/login ==================");
//        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
//        System.out.println("authentication: " + principalDetails.getMember());
//        System.out.println("userDetails: " + pd.getUsername());
//
//        return "세션 정보 확인하기";
//    }
//
//    @GetMapping("/test/oauth/login")
//    public @ResponseBody String testOAuthLogin(Authentication auth) {
//        System.out.println("/test/oauth/login ==================");
//        OAuth2User oAuth2User = (OAuth2User) auth.getPrincipal();
//        System.out.println("authentication: " + oAuth2User.getAttributes());
//
//        return "OAuth 세션 정보 확인하기";
//    }



}
