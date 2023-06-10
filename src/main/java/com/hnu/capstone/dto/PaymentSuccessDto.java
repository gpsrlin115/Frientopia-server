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
    private String impUID;
    private String merchantUID;

    public PaymentSuccessDto(Long amount, String buyerEmail, String impUID, String merchantUID) {
        this.amount = amount;
        this.buyerEmail = buyerEmail;
        this.impUID = impUID;
        this.merchantUID = merchantUID;
    }

    public Payment toEntity() {
        return Payment.builder()
                .amount(amount)
                .user(user)
                .impUID(impUID)
                .merchantUID(merchantUID)
                .build();
    }
}
