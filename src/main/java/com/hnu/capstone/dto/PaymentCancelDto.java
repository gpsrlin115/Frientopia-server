package com.hnu.capstone.dto;

import com.hnu.capstone.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCancelDto {
    private String impUID;
    private String reason;
}