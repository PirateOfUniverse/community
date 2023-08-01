package com.community.app.service;

import com.community.app.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("회원가입 확인")
    void register() throws Exception {
        // given
        Member member = new Member();

        String rawPasswd = "1234";
        String encodePasswd = encoder.encode(rawPasswd);

        System.out.println(rawPasswd);
        System.out.println(encodePasswd);

        member.setEmail("malang@naver.com");
        member.setPasswd(encodePasswd);
        member.setNick("malanggu");

        // when
        int insertTest = memberService.insertMember(member);

        // then
        System.out.println(insertTest);
    }

    @Test
    @DisplayName("중복 회원 확인")
    void duplicateMember() {
        // given
        Member member1 = new Member();
        member1.setEmail("malang@naver.com");
        member1.setPasswd("a");
        member1.setNick("말랑");

        Member member2 = new Member();
        member2.setEmail("malang@naver.com");
        member2.setPasswd("b");
        member2.setNick("aaa");

        // when
        //List<Member> memberList = memberService.
        memberService.insertMember(member1);
        int dupEmail = memberService.duplicateEmail(member2);
        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () ->
                memberService.insertMember(member2));

        // then
        assertThat(dupEmail).isEqualTo(1);
    }

    @Test
    @DisplayName("로그인 테스트")
    void loginTest() {
        // given
        Member member = new Member();
        member.setEmail("malang@naver.com");
        String passwd = "1234";
        String enPasswd = encoder.encode(passwd);
        member.setPasswd(enPasswd);
        member.setNick("말랑");
        memberService.insertMember(member);

        // when
        String findEncodedPasswd = memberService.compareEmailAndPasswd(member.getEmail());
        boolean match = encoder.matches(passwd, findEncodedPasswd);


        // then
        if(match == true) {
            System.out.println("로그인 성공");
        } else {
            System.out.println("로그인 실패");
        }

    }


}
