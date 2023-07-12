package com.community.app.security.oauth.provider;

public interface OAuth2MemberInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
