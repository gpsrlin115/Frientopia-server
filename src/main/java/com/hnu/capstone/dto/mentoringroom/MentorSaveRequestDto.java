package com.hnu.capstone.dto.mentoringroom;

import com.hnu.capstone.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentorSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String fileName;
    private MentoringRoom mentoringRoom;
    private User user;

    public MentorSaveRequestDto(String author) {
        this.author = author;
    }

    public MentoringRoomPost toEntity() {
        return MentoringRoomPost.builder()
                .mentoringRoom(mentoringRoom)
                .title(title)
                .content(content)
                .author(author)
                .user(user)
                .fileName(fileName)
                .mentoringRoomCategory(MentoringRoomCategory.MENTOR)
                .build();
    }
}
