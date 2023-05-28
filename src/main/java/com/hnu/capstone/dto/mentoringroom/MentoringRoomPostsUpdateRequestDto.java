package com.hnu.capstone.dto.mentoringroom;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MentoringRoomPostsUpdateRequestDto {
        private String title;
        private String content;

        @Builder
        public MentoringRoomPostsUpdateRequestDto(String title, String content) {
            this.title = title;
            this.content = content;
        }
}
