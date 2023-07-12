package com.community.app.security;

import com.community.app.domain.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String nick, email, role;
    private int idx;

    public SessionMember(Member member) {
        this.idx = member.getIdx();
        this.nick = member.getNick();
        this.email = member.getEmail();
        this.role = member.getRole();
    }
}
