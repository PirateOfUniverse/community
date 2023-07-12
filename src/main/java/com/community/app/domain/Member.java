package com.community.app.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class Member {

    private int idx; // 회원 고유 번호
    private String email; // 회원 이메일
    private String passwd; // 비밀번호
    private String nick; // 회원 닉네임
    private String role; // 회원/관리자 구분용(default = member)
    private String provider;
    private String providerId;
    private String regdate; // 가입일자

    Collection<? extends GrantedAuthority> authorities;

    public Member() {}

    public Member(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.passwd = passwd;
        this.authorities = authorities;
    }

    @Builder
    public Member(String email, String nick, String role,
                  String provider, String providerId) {
        this.email = email;
        this.nick = nick;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public Member update(String nick) {
        this.nick = nick;
        return this;
    }

    public Member(int idx, String email, String passwd, String nick,
                  String role, String provider, String providerId,
                  String regdate) {
        this.idx = idx;
        this.email = email;
        this.passwd = passwd;
        this.nick = nick;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.regdate = regdate;
    }
}
