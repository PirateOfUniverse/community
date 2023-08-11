package com.community.app.security;

import com.community.app.security.oauth.PrincipalOauth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true) // secured어노테이션(=PreAuthorize) 활성화
public class SecurityConfiguration {

    private final PrincipalOauth2UserService principalOauth2UserService;

    public SecurityConfiguration(PrincipalOauth2UserService principalOauth2UserService) {
        this.principalOauth2UserService = principalOauth2UserService;
    }

    @Bean
    public BCryptPasswordEncoder encodePasswd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 권한에 따라 허용하는 url을 설정

        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/board/**", "/search", "/postDetail").authenticated()
                .antMatchers("/admin/**").access("hasAuthority('ROLE_ADMIN')")
                .antMatchers("/login", "/register").permitAll()
                .anyRequest().permitAll();

        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("passwd")
                .defaultSuccessUrl("/");

        http
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

        http
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        // 구글로그인이 완료된 후 후처리 필요
        // 1.코드받기(인증) 2. 액세스 토큰(권한)
        // 3. 사용자 프로필 정보 가져오기 4. 그 정보를 토대로 회원가입 자동진행
        // 4-2. 추가적인 회원가입이 필요하면 다른 창이 나와야함

        return http.build();
    }
}
