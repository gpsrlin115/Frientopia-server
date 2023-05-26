package com.hnu.capstone.controller;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.dto.PostsListResponseDto;
import com.hnu.capstone.dto.PostsSaveRequestDto;
import com.hnu.capstone.dto.PostsUpdateRequestDto;
import com.hnu.capstone.service.MentoringMappingService;
import com.hnu.capstone.service.MentoringRoomService;
import com.hnu.capstone.service.UserService;
import com.hnu.capstone.domain.*;
import com.hnu.capstone.dto.PostsResponseDto;
import com.hnu.capstone.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MentoringRoomPostsController {
    private final PostsService postsService;
    private final HttpSession httpSession;
    private final UserService userService;
    private final MentoringMappingService mentoringMappingService;
    private final MentoringRoomService mentoringRoomService;

    @GetMapping("/mentoring/room/{room_id}")
    public String MentoringRoomForm(@PathVariable Long room_id){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long i = mentoringRoomService.mentoringRoomEnter(user, room_id); //enter 성공시 1L, 신청이 안 되어 있을 시 0L, 멘토링 룸이 존재하지 않을 시 null;

        if(i != null){
            if(i == 1L){
                return room_id + "에 입장하였습니다.";
            }else if(i == 0L){
                return room_id + "에 신청하지 않았습니다.";
            }
        }
        return "멘토링 룸이 존재하지 않습니다.";
    }
}
