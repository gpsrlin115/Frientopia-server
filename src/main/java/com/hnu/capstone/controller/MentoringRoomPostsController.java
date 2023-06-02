package com.hnu.capstone.controller;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.domain.MentoringMappingRepository;
import com.hnu.capstone.domain.PostsRepository;
import com.hnu.capstone.domain.User;
import com.hnu.capstone.dto.mentoringroom.MentoringRoomPostsUpdateRequestDto;
import com.hnu.capstone.dto.mentoringroom.MentorSaveRequestDto;
import com.hnu.capstone.dto.mentoringroom.BoardSaveRequestDto;
import com.hnu.capstone.dto.mentoringroom.VideoSaveRequestDto;
import com.hnu.capstone.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/mentoring/room")
@Controller
public class MentoringRoomPostsController {
    private final PostsService postsService;
    private final HttpSession httpSession;
    private final UserService userService;
    private final PostsRepository postsRepository;
    private final MentoringMappingService mentoringMappingService;
    private final MentoringMappingRepository mentoringMappingRepository;
    private final MentoringRoomService mentoringRoomService;
    private final MentoringRoomPostsService mentoringRoomPostsService;

    @PostMapping("/{room_id}/mentor/posts")
    public String saveNotice(@PathVariable Long room_id, @ModelAttribute MentorSaveRequestDto requestDto) throws IOException {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }

        Long post_num = userService.UserToPost(sessionUser, requestDto, room_id);

        return "redirect:/mentoring/room/{room_id}/mentor";
    }

    @PostMapping("/{room_id}/board/posts")
    public String saveReference(@PathVariable Long room_id, @ModelAttribute BoardSaveRequestDto requestDto) throws IOException {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }

        Long post_num = userService.UserToPost(sessionUser, requestDto, room_id);

        return "redirect:/mentoring/room/{room_id}/board";
    }

    @PostMapping("/{room_id}/video/posts")
    public String saveVideo(@PathVariable Long room_id, @ModelAttribute VideoSaveRequestDto requestDto) throws IOException {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }

        Long post_num = userService.UserToPost(sessionUser, requestDto, room_id);

        return "redirect:/mentoring/room/{room_id}/video";
    }

    @PutMapping("/{room_id}/update/{id}")
    public String update(@PathVariable Long room_id, @PathVariable Long id, @ModelAttribute MentoringRoomPostsUpdateRequestDto requestDto) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }

        mentoringRoomPostsService.update(id, requestDto);

        return "redirect:/mentoring/room/{room_id}";
    }
}
