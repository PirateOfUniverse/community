package com.community.app.security.oauth.provider;

import java.util.Map;

public class NaverMemberInfo implements OAuth2MemberInfo{

    private Map<String, Object> attributes;

    public NaverMemberInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("nickname");
    }

    public int getIdx() {
        return (int) attributes.get("idx");
    }
}
