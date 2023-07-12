package com.community.app.security.oauth;

import com.community.app.domain.Member;
import com.community.app.dto.MemberUpdateDto;
import com.community.app.security.oauth.provider.GoogleMemberInfo;
import com.community.app.security.oauth.provider.NaverMemberInfo;
import com.community.app.security.oauth.provider.OAuth2MemberInfo;
import com.community.app.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // oauth2 로그인 회원가입
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2MemberInfo oAuth2UserInfo = null;

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if(registrationId.equals("google")) {
            oAuth2UserInfo = new GoogleMemberInfo(oAuth2User.getAttributes());
        } else if(registrationId.equals("naver")) {
            oAuth2UserInfo = new NaverMemberInfo((Map) oAuth2User.getAttributes().get("response"));
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();

        Optional<Member> memberOptional =
                memberService.findProviderAndProviderId(provider, providerId);

        Member member = new Member();
        MemberUpdateDto dto = new MemberUpdateDto();
        if(memberOptional.isPresent()) {
            member = memberOptional.get();
            // member 존재 시 업데이트 해줌
            member.setEmail(oAuth2UserInfo.getEmail());
            memberService.updateMember(dto);
        } else if (!memberOptional.isPresent()){
            member = Member.builder()
                    .nick(oAuth2UserInfo.getName())
                    .email(oAuth2UserInfo.getEmail())
                    .role("ROLE_MEMBER")
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            memberService.insertMember(member);
        }

        int idx = member.getIdx();
        String email = member.getEmail();

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("idx", idx);
        attributes.put("email", email);

        //return new PrincipalDetails(member, oAuth2User.getAttributes(), member.getIdx());
        return new PrincipalDetails(member, attributes, idx, email);
    }

}
