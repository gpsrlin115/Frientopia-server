package com.hnu.capstone.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoom_id")
    private Long id;
    private String name;

    @Builder
    public ChatRoom(String name) {
        this.name = name;
    }

    public static ChatRoom createRoom(String name) {
        return ChatRoom.builder()
                .name(name)
                .build();
    }
}
