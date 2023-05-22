package com.hnu.capstone.domain;

import com.hnu.capstone.dto.PostsResponseDto;
import com.hnu.capstone.dto.PostsSaveRequestDto;
import com.hnu.capstone.service.PostsService;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import java.util.*;
import java.util.function.Function;

@Getter
@NoArgsConstructor
@Table(name = "Users")
@Entity
public class User extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Id
    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String gen;

    @Column
    private int age;

    @Column
    private String major;

    @Column
    private long phoneNum;

    @Column
    private String introduce;

    @Column
    private double ratingScore;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Posts> posts = new ArrayList<>();

    @OneToMany (mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MentoringMapping> mentoringMappings = new ArrayList<>();

    public void PostAddUser(PostsSaveRequestDto post){
        post.setUser(this);
    }

    public void UserAddPost(Posts post){
        this.getPosts().add(post);
    }

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
