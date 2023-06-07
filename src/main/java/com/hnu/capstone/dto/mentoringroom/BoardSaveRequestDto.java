package com.hnu.capstone.dto.mentoringroom;

import com.hnu.capstone.domain.MentoringRoom;
import com.hnu.capstone.domain.MentoringRoomCategory;
import com.hnu.capstone.domain.MentoringRoomPost;
import com.hnu.capstone.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String fileName;
    private MentoringRoom mentoringRoom;
    private User user;
    private MultipartFile file;

    public BoardSaveRequestDto(String author) {
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
                .mentoringRoomCategory(MentoringRoomCategory.BOARD)
                .build();
    }
    public void addFileName(String storeFileName){
        this.fileName = storeFileName;
    }
}
