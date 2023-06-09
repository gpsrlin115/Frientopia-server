package com.hnu.capstone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @Column(nullable = false)
    private Long amount;


    @Builder
    public Payment(Long id, User user, Long amount) {
        this.id = id;
        this.user = user;
        this.amount = amount;
    }

    public void setUser(User user){
        user.getPayments().add(this);
        this.user = user;
    }
}
