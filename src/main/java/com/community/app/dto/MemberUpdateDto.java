package com.community.app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateDto {

    private int idx;
    private String passwd;
    private String nick;

    public MemberUpdateDto() {}

    public MemberUpdateDto(int idx, String nick) {
        this.idx = idx;
        this.nick = nick;
    }
    public MemberUpdateDto(int idx, String passwd, String nick) {
        this.idx = idx;
        this.passwd = passwd;
        this.nick = nick;
    }

}
