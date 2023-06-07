package com.hnu.capstone.dto;

import com.hnu.capstone.domain.Posts;
import com.hnu.capstone.domain.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private User user;
    private String fileName;
    private MultipartFile file;
    private double latitude;  // 위도
    private double longitude; // 경도
    private String address;   // 멘토링 주소

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, User user, double latitude, double longitude, String address) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public PostsSaveRequestDto(String author) {
        this.author = author;
    }


    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .user(user)
                .fileName(fileName)
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .build();
    }
    /* 서버가 관리하는 파일명 추가 */
    public void addFileName(String storeFileName){
        this.fileName = storeFileName;
    }
}
