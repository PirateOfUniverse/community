package com.community.app.dto;

import lombok.Data;

@Data
public class PostUpdateDto {

    private int pidx;
    private String title;
    private String category;
    private String content;

    public PostUpdateDto() {}

    public PostUpdateDto(int pidx, String title, String category, String content) {
        this.pidx = pidx;
        this.title = title;
        this.category = category;
        this.content = content;
    }
}
