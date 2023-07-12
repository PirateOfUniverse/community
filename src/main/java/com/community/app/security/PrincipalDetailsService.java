package com.community.app.security;

import com.community.app.domain.Member;
import com.community.app.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

// 시큐리티에서 loginProcessingUrl("/login");
// login요청이 오면 자동으로 UserDetailsService타입으로 IoC되어있는 
// loadUserByUsername 함수 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    // 시큐리티 내용 외 파라미터를 추가하고 싶을때 아래 사용
    // Controller에서 Auth점검 시 UserCustom으로 받아야함
    // 예) @AuthenticationPrincipal User user >> @AuthenticationPrincipal UserCustom user
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    // 시큐리티 session(내부 Authentication(내부UserDetails))
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberService.selectMemberOne(email);
        System.out.println("일반 로그인");

        if(member == null) {
            return null;
        }

        UserCustom userCustom = new UserCustom(member.getNick(),
                member.getPasswd(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked,
                authorities(member), member.getIdx(), member.getEmail());

        return userCustom;


//            PrincipalDetails principalDetails = new PrincipalDetails(member);
//            principalDetails.setIdx(member.getIdx());
//            return principalDetails;

    }

    private static Collection authorities(Member member) {
        Collection authorities = new ArrayList<>();
        if(member.getRole().equals("ROLE_ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        }
        return authorities;
    }
}
