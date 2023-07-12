package com.community.app.security;

import com.community.app.security.oauth.PrincipalDetails;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter @Setter
@ToString
public class UserCustom extends User {

    private static final long serialVersionUID
            = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private PrincipalDetails principalDetails;

    // 멤버 정보 추가
    private int idx;
    private String email;
    private String username; // 세션값 변경을 위해서 받음

    public UserCustom(String username, String password,
                      boolean enabled, boolean accountNonExpired,
                      boolean credentialsNonExpired, boolean accountNonLocked,
                      Collection authorities, int idx, String email) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
        this.idx = idx;
        this.email = email;
        this.username = username; // 세션 변경 위해서 받음
    }

    public int getIdx() {
        return idx;
    }
    public String getEmail() {
        return email;
    }

}
