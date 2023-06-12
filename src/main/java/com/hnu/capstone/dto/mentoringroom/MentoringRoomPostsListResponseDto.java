package com.hnu.capstone.dto.mentoringroom;

import com.hnu.capstone.domain.MentoringRoomCategory;
import com.hnu.capstone.domain.MentoringRoomPost;
import com.hnu.capstone.domain.Posts;
import com.hnu.capstone.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MentoringRoomPostsListResponseDto {
    private long id;
    private MentoringRoomCategory category;
    private String title;
    private String author;
    private User user;
    private Long mentoringRoomId;
    private LocalDateTime modifiedDate;

    public String getModifiedDate() {
        String formatDate = modifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return formatDate;
    }

    public MentoringRoomPostsListResponseDto(MentoringRoomPost entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.user = entity.getUser();
        this.modifiedDate = entity.getModifiedDate();
        this.category = entity.getMentoringRoomCategory();
        this.mentoringRoomId = entity.getMentoringRoom().getId();
    }
}
