package com.hnu.capstone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column
    private String author;

    @Column
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @OneToMany (mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MentoringMapping> mentoringMappings = new ArrayList<>();

    @OneToOne (mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private MentoringRoom mentoringRoom;

    @Builder
    public Posts(Long id, String title, String content, String author, User user, String fileName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.user = user;
        this.fileName = fileName;
        }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
