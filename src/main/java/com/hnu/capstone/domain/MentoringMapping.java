package com.hnu.capstone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mentoring_mapping") // 테이블 이름 설정
public class MentoringMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false) // 게시글 외래 키 설정
    private Posts registeredStudy;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 사용자 이메일 외래 키 설정
    private User participant;

    @Builder
    public MentoringMapping(User participant, Posts registeredStudy) {
        this.participant = participant;
        this.registeredStudy = registeredStudy;
    }
}
