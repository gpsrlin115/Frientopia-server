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
        String i = mentoringRoomService.mentoringRoomEnter(user, room_id);

        return i;
    }
}
