package com.hnu.capstone.controller;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.domain.*;
import com.hnu.capstone.dto.PaymentSuccessDto;
import com.hnu.capstone.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping("/payment")
public class PayController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    private final UserService userService;
    private final PostsRepository postsRepository;
    private final MentoringMappingService mentoringMappingService;
    private final MentoringMappingRepository mentoringMappingRepository;
    private final MentoringRoomService mentoringRoomService;
    private final PayService payService;


    @Value("#{config['iamport.apiKey']}")
    private String apiKey;

    @Value("#{config['iamport.apiSecret']}")
    private String apiSecret;


    @GetMapping("")
    public String payment(Model model) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        model.addAttribute("buyer", sessionUser);
        return "payment";
    }

    @PostMapping("/success")
    public String paymentSuccess(@RequestBody PaymentSuccessDto paymentSuccessDto){
        paymentSuccessDto.setUser(userService.SelectUser(paymentSuccessDto.getBuyerEmail()));
        Long paymentID = payService.save(paymentSuccessDto);
        if(paymentID != null){
            Payment payment = payService.findById(paymentID);
            payment.getUser().addPoint(payment.getAmount());
            userService.UpdateUser(payment.getUser());
        }

        return "payment";
    }
}
