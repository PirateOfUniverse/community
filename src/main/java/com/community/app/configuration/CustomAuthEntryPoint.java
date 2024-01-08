package com.community.app.configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    // 사용자 로그인 안됐을때 엔트리 포인트 지정하는 custom클래스
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // 사용자가 인증되지 않았을때(로그인하지 않았을때) 리다이렉트할 url 지정
        response.sendRedirect("/notMember");

    }
}
