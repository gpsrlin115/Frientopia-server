package com.hnu.capstone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "mentoring_room") // 테이블 이름 설정
public class MentoringRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false) // 게시글 외래 키 설정
    private Posts post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 사용자 이메일 외래 키 설정
    private User user;

    @Builder
    public MentoringRoom(Posts post, User user) {
        this.post = post;
        this.user = user;
    }
}
