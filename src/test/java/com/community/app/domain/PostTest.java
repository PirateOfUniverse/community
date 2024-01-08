package com.community.app.domain;


import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @Test
    public void builder() {
        // builder가 있는지 확인하는 테스트
        Post post = Post.builder().build();
        assertThat(post).isNotNull(); // alt + enter = quick fix
    }

    @Test
    public void javaBean() {
        // Given
        // 중복제거(리팩토링) = ctrl + alt + v(String content처럼 중복된 내용 제거해서 하나로합치기)
        String title = "안녕하세요";
        String content = "이것은테스트입니다";

        // When
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        // Then
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

}