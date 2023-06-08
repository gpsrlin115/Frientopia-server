package com.hnu.capstone.dto;

import com.hnu.capstone.domain.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private String fileName;
    private double latitude;  // 위도
    private double longitude; // 경도
    private String address;   // 멘토링 주소
    private Long point;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.fileName = entity.getFileName();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.address = entity.getAddress();
        this.point = entity.getPoint();
    }
}
