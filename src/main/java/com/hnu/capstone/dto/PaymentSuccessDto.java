package com.hnu.capstone.dto;

import com.hnu.capstone.domain.Payment;
import com.hnu.capstone.domain.Posts;
import com.hnu.capstone.domain.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSuccessDto {
    private Long amount;
    private String buyerEmail;
    private User user;

    public PaymentSuccessDto(Long amount, String buyerEmail) {
        this.amount = amount;
        this.buyerEmail = buyerEmail;
    }

    public Payment toEntity() {
        return Payment.builder()
                .amount(amount)
                .user(user)
                .build();
    }
}
