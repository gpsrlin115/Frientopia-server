package com.hnu.capstone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.Collection;
>>>>>>> master
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "Users")
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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

    @OneToMany(mappedBy = "user")
    private List<Posts> posts = new ArrayList<>();



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

<<<<<<< HEAD
=======
/*    public void setAuthority(){
        this.authority = new SimpleGrantedAuthority(this.getRoleKey());
    }*/
>>>>>>> master
}
