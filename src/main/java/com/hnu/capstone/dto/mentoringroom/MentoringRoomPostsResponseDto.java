package com.hnu.capstone.dto.mentoringroom;

import com.hnu.capstone.domain.MentoringRoomPost;
import com.hnu.capstone.domain.Posts;
import lombok.Getter;

@Getter
public class MentoringRoomPostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public MentoringRoomPostsResponseDto(MentoringRoomPost entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
