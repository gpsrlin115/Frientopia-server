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

    @Column
    private double latitude;  // 위도

    @Column
    private double longitude; // 경도

    @Column
    private String address;   // 멘토링 주소

    @Column
    private Long point;       // 신청 시 가격


    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @OneToMany (mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MentoringMapping> mentoringMappings = new ArrayList<>();

    @OneToOne (mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private MentoringRoom mentoringRoom;

    @Builder
    public Posts(Long id, String title, String content, String author, User user, String fileName, double latitude, double longitude, String address) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.user = user;
        this.fileName = fileName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.point = 100L;
        }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
