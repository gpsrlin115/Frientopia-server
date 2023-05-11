package com.hnu.capstone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "Users")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = true)
    private String gen;

    @Column(nullable = true)
    private int age;

    @Column(nullable = true)
    private String major;

    @Column(nullable = true)
    private long phoneNum;

    @Column(nullable = true)
    private String introduce;

    @Column(nullable = true)
    private double rating_score;


    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String gen, int age, String major, long phoneNum, String introduce) {
        this.gen = gen;
        this.age = age;
        this.major = major;
        this.phoneNum = phoneNum;
        this.introduce = introduce;

        return this;
    }

    public User roleUpdate(Role role){
        this.role = role;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
