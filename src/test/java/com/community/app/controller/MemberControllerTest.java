package com.community.app.controller;

import com.community.app.domain.Member;
import com.community.app.dto.MemberUpdateDto;
import com.community.app.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberControllerTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberController controller;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void loginTest() throws Exception {
        // given
        Member member = new Member(email, password, authorities);
        member.setEmail("wjdals3486@naver.com");
        member.setPasswd("1234");
        member.setNick("malanggu");

        String encodedPasswd = encoder.encode(member.getPasswd());
        member.setPasswd(encodedPasswd);

        memberService.insertMember(member);

        String insertedPasswd1 = "1234";
        String insertedPasswd2 = "abcd";

        // when
        String loginTest = memberService.compareEmailAndPasswd(member.getEmail());
        boolean match1 = encoder.matches(insertedPasswd1, loginTest);
        boolean match2 = encoder.matches(insertedPasswd2, loginTest);

        // then
        System.out.println(member.getPasswd());
        System.out.println("로그인 완료 = true, 로그인 실패 = false");
        System.out.println("올바른 비밀번호를 넣었을때 로그인: " + match1);
        System.out.println("틀린 비밀번호를 넣었을때 로그인: " + match2);

    }

    @Test
    void updateTest() throws Exception {
        // given
        Member member = new Member(email, password, authorities);
        String passwd = "1234";
        member.setEmail("wjdals3486@naver.com");
        member.setNick("malanggu");

        String encodedPasswd = encoder.encode(passwd);
        member.setPasswd(encodedPasswd);

        memberService.insertMember(member);
        System.out.println(member.getPasswd());

        // when
        MemberUpdateDto dto = new MemberUpdateDto();
        String str = "aa";
        dto.setNick(str);
        memberService.updateMember(dto);
        String loginTest = memberService.compareEmailAndPasswd(member.getEmail());
        boolean match = encoder.matches(passwd, loginTest);

        // then
        System.out.println(member.getPasswd());
        System.out.println("로그인 완료 = true, 로그인 실패 = false");
        System.out.println("올바른 비밀번호를 넣었을때 로그인: " + match);
    }

}
