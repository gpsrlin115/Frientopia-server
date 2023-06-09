package com.hnu.capstone.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String impUID;

    @Column(nullable = false)
    private String merchantUID;


    @Builder
    public Payment(Long id, User user, Long amount, String impUID, String merchantUID) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.impUID = impUID;
        this.merchantUID = merchantUID;
    }

    public void setUser(User user){
        user.getPayments().add(this);
        this.user = user;
    }
}
