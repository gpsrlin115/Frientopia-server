package com.hnu.capstone.service;

import com.hnu.capstone.domain.Payment;
import com.hnu.capstone.domain.PayRepository;
import com.hnu.capstone.domain.User;
import com.hnu.capstone.dto.PaymentSuccessDto;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PayService {
    private final UserService userService;
    private final PayRepository payRepository;

    /* 악의적인 수정을 방지하기 위해 수정은 구현 x */

    @Transactional
    public Long save(@NotNull PaymentSuccessDto paymentSuccessDto){
        return payRepository.save(paymentSuccessDto.toEntity()).getId();
    }

    @Transactional
    public Long delete(Long id){
        payRepository.deleteById(id);
        return id;
    }

    public Payment findById(Long id){
        if(payRepository.findById(id).isPresent()){
            return payRepository.findById(id).get();
        }
        return null;
    }
    public List<Payment> findByUserEmail(String userEmail){
        User user = userService.SelectUser(userEmail);
        return payRepository.findByUser(user);
    }

    public Payment findByImpUID(String impUID){
        if(payRepository.findByImpUID(impUID).isPresent()){
            return payRepository.findByImpUID(impUID).get();
        }
        return null;
    }
}
