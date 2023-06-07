package com.hnu.capstone.dto;

import com.hnu.capstone.domain.Posts;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class PostsListResponseDto {

    private long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;
    private double latitude;  // 위도
    private double longitude; // 경도
    private String address;   // 멘토링 주소

    public String getModifiedDate() {
        String formatDate = modifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return formatDate;
    }

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.address = entity.getAddress();
    }
}
