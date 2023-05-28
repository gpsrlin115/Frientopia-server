package com.hnu.capstone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MentoringRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="post_id")
    private Posts post;

    @OneToMany (mappedBy = "mentoringRoom", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MentoringMapping> mentoringMappings = new ArrayList<>();

    @OneToMany(mappedBy = "mentoringRoom", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MentoringRoomPost> mentoringRoomPosts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @Builder
    public MentoringRoom(Posts post, User user) {
        this.post = post;
        this.user = user;
        user.getMentoringRoom().add(this);
    }

    public List<User> getMentee(){
        List<User> mentee = new ArrayList<>();
        for (MentoringMapping m : mentoringMappings) {
            mentee.add(m.getUser());
        }
        return mentee;
    }
}
