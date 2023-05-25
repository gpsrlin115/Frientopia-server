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
public class MentoringRoomPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MentoringRoomCategory mentoringRoomCategory;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String author;

    @Column
    private String file;

    @ManyToOne
    @JoinColumn(name="mentoring_room")
    private MentoringRoom mentoringRoom;

    @ManyToOne
    @JoinColumn(name="user_email")
    private User user;

    @Builder
    public MentoringRoomPost(Long id, MentoringRoomCategory mentoringRoomCategory, String title, String content, String author, MentoringRoom mentoringRoom, User user) {
        this.id = id;
        this.mentoringRoom = mentoringRoom;
        this.title = title;
        this.content = content;
        this.author = author;
        this.mentoringRoomCategory = mentoringRoomCategory;
        this.user = user;
    }

    public void MentoringRoomPostToRoom(MentoringRoom mentoringRoom){
        this.setMentoringRoom(mentoringRoom);
        mentoringRoom.getMentoringRoomPosts().add(this);
    }

    public void MentoringRoomPostToUser(User user){
        this.setUser(user);
        user.getMentoringRoomPosts().add(this);
    }
}