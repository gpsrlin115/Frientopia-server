package com.hnu.capstone.controller;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.domain.*;
import com.hnu.capstone.dto.PaymentCancelDto;
import com.hnu.capstone.dto.PaymentSuccessDto;
import com.hnu.capstone.service.*;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.io.Console;
import java.util.LinkedHashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PayApiController {

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


    @PostMapping("/success")
    public String paymentSuccess(@RequestBody PaymentSuccessDto paymentSuccessDto){
        paymentSuccessDto.setUser(userService.SelectUser(paymentSuccessDto.getBuyerEmail()));
        Long paymentID = payService.save(paymentSuccessDto);
        if(paymentID != null){
            Payment payment = payService.findById(paymentID);
            payment.getUser().addPoint(payment.getAmount());
            userService.UpdateUser(payment.getUser());
        }

        return paymentSuccessDto.getMerchantUID();
    }

    @PostMapping("/getToken")
    public ResponseEntity<JSONObject> getToken(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject body = new JSONObject();
        body.put("imp_key", imp_key);
        body.put("imp_secret", imp_secret);

        try {
            HttpEntity<JSONObject> entity = new HttpEntity<>(body, headers);
            ResponseEntity<JSONObject> token = restTemplate.postForEntity("https://api.iamport.kr/users/getToken", entity, JSONObject.class);
            return token;
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping("/cancel")
    public String cancelPay(@RequestBody PaymentCancelDto paymentCancelDto){
        Payment payment = payService.findByImpUID(paymentCancelDto.getImpUID());

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(paymentCancelDto.getImpUID() != null){   // impUID(결제번호)가 null이 아닐 시
            if(payment.getUser().getPoint() >= payment.getAmount()){
                JSONObject body = new JSONObject();                 // 아임포트에 보낼 정보 설정
                body.put("imp_uid", paymentCancelDto.getImpUID());  // 결제 정보
                body.put("reason", paymentCancelDto.getReason());   // 사유

                ResponseEntity<JSONObject> tokenResponse = getToken(); // getToken() 메서드 호출

                if (tokenResponse != null && tokenResponse.getStatusCode().is2xxSuccessful()) {
                    JSONObject token = (JSONObject) tokenResponse.getBody();
                    // 반환된 토큰 데이터 사용 가능
                    try {
                        Map responseHash = (Map) token.get("response");      // "response" 객체를 Map 형식의 "responseHash"로 추출
                        JSONObject response = new JSONObject();
                        response.putAll(responseHash);                       // JSONObject 형식의 "response"
                        String accessToken = response.get("access_token").toString();       // accessToken  값 가져오기
                        headers.add("Authorization", accessToken);               // header에 accessToken 넣기
                        HttpEntity<JSONObject> entity = new HttpEntity<>(body, headers);
                        ResponseEntity<JSONObject> cancel = restTemplate.postForEntity("https://api.iamport.kr/payments/cancel", entity, JSONObject.class); // url로 전송

                        payment.getUser().subPoint(payment.getAmount());        // 포인트 차감
                        payService.delete(payment.getId());                     // 페이먼트 삭제
                        //return cancel.toString();

                        return "취소에 성공했습니다.";
                    } catch (Exception e) {
                        e.printStackTrace(); // 예외 출력
                        //return null;
                        return "취소 과정에서 에러가 발생했습니다.";
                    }
                }
            }
            return "포인트가 부족합니다.";
        }
        return "취소에 실패했습니다.";
    }
}
