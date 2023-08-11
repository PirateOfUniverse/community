package com.community.app.security.oauth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인 진행
// 로그인 진행 완료되면 시큐리티가 session을 만들어준다
// (Security ContextHolder)
// 오브젝트 -> Authentication 타입 객체
// Authenntication 안에 Member 정보 있어야함
// Member 오브젝트 타입 -> UserDetails 타입 객체

import com.community.app.domain.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Security Session -> Authentication -> UserDetails

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private int idx;
    private String email;
    private Member member;
    private static final long serialVersionUID = 1L;
    private Map<String, Object> attributes;

    public PrincipalDetails(Member member) {
        //일반로그인
        this.member = member;
    }

    public PrincipalDetails(Member member, Map<String, Object> attributes, int idx, String email) {
        // OAuth로그인 생성자
        this.member = member;
        this.attributes = attributes;
        this.idx = idx;
        this.email = email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // member의 권한 리턴
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(()->{ return member.getRole();});
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPasswd();
    }

    @Override
    public String getUsername() {
        return member.getNick();
    }

    public void setUsername(String username) {
        member.setNick(username);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 사이트에서 1년동안 로그인 안하면 휴-면 계정으로 전환할때 사용한다
        return true;
    }
    @Override
    public String getName() {
        return member.getIdx()+"";
    }

    public Member getMember() {
        // 컨트롤러에서 세션정보가 담긴 principalDetails에서 member를 가져와서
        // 회원의 고유 번호인 idx를 가져오기 위함.. 이라고 했지만
        // 생각해보니까 이렇게 안가져와도 될것같은데??
        return member;
    }

    public int getIdx() {
        return idx;
    }

    public String getEmail() {
        return email;
    }
}
