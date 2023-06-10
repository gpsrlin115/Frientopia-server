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
import java.io.Console;

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
    private String imp_key;

    @Value("#{config['iamport.apiSecret']}")
    private String imp_secret;


    @GetMapping("")
    public String payment(Model model) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        model.addAttribute("buyer", sessionUser);
        return "payment";
    }

    @GetMapping("/cancel")
    public String paymentCancle(@RequestParam("impUID") String impUID, Model model){
        Payment payment = payService.findByImpUID(impUID);
        model.addAttribute("payment", payment);

        return "paymentCancel";
    }
}
