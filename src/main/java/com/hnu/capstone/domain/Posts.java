package com.hnu.capstone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.hnu.capstone.domain.MentoringMapping;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "registeredStudy")
    private Set<MentoringMapping> participants = new HashSet<>();
    private Integer currentStudyMate;


    @Builder
    public Posts(String title, String content, String author, User user) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.user = user;
        }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addParticipants(MentoringMapping mapper) {
        this.participants.add(mapper);
    }

    public void increaseOneStudyMate() {
        this.currentStudyMate++;
    }

    public void removeParticipant(MentoringMapping participant) {
        this.participants.remove(participant);
        participant.setRegisteredStudy(null);
        this.currentStudyMate--;
    }
}
