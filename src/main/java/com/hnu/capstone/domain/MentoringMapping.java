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
    private Posts post;

    @ManyToOne
    @JoinColumn(name = "user_email", nullable = false) // 사용자 이메일 외래 키 설정
    private User user;

    @Builder
    public MentoringMapping(Posts post, User user) {
        this.post = post;
        this.user = user;
    }

    public void MentoringMappingAddUser(User user){
        this.setUser(user);
    }

    public void UserAddMentoringMapping(User user){
        user.getMentoringMappings().add(this);
    }

    public void MentoringMappingAddPost(Posts post){
        this.setPost(post);
    }

    public void PostAddMentoringMapping(Posts post){
        post.getMentoringMappings().add(this);
    }

}
