package com.hnu.capstone.controller;

import com.hnu.capstone.domain.ApiResponseResult;
import com.hnu.capstone.service.CompileBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CompileController {
    private final CompileBuilder builder;

    @PostMapping("/compile")
    public Map<String, Object> compileCode(@RequestBody Map<String, Object> input) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // compile input code
        Object obj = builder.compileCode(input.get("code").toString());

        // compile 결과 타입이 String일 경우 컴파일 실패 후 메시지 반환으로 판단하여 처리
        if(obj instanceof String) {
            returnMap.put("result", ApiResponseResult.FAIL.getText());
            returnMap.put("SystemOut", obj.toString());
            return returnMap;
        }

        // 실행 후 결과 전달 받음
        long beforeTime = System.currentTimeMillis();

        // 코드 실행
        Map<String, Object> output = builder.runObject(obj);
        long afterTime = System.currentTimeMillis();

        // 코드 실행 결과 저장
        returnMap.putAll(output);
        // 소요시간
        returnMap.put("performance", (afterTime - beforeTime));

        // 결과 체크
        try {
            returnMap.put("SystemOut", returnMap.get("SystemOut").toString());
        }catch (Exception e) {
            returnMap.put("result", ApiResponseResult.FAIL.getText());
            returnMap.put("SystemOut", returnMap.get("SystemOut").toString() + "예상치 못한 오류로 검사에 실패했습니다.");
        }


        return returnMap;
    }
}
