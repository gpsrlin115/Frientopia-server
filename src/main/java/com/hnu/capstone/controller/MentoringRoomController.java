package com.hnu.capstone.controller;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.dto.mentoringroom.MentoringRoomPostsListResponseDto;
import com.hnu.capstone.dto.mentoringroom.MentoringRoomPostsResponseDto;
import com.hnu.capstone.dto.mentoringroom.MentoringRoomPostsUpdateRequestDto;
import com.hnu.capstone.dto.mentoringroom.MentorSaveRequestDto;
import com.hnu.capstone.service.*;
import com.hnu.capstone.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping("/mentoring/room")
@Controller
public class MentoringRoomController {
    private final PostsService postsService;
    private final HttpSession httpSession;
    private final UserService userService;
    private final MentoringMappingService mentoringMappingService;
    private final MentoringRoomService mentoringRoomService;
    private final MentoringRoomPostsService mentoringRoomPostsService;
    private final ChatService chatService;

    @GetMapping("/{room_id}")
    public String MentoringRoomForm(@PathVariable Long room_id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id); //enter 성공시 1L, 신청이 안 되어 있을 시 0L, 멘토링 룸이 존재하지 않을 시 -1L, 멘토일 시 2L;
        System.out.println(result != 1L && result != 2L);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }

        List<MentoringRoomPostsListResponseDto> posts = new ArrayList<>();
        List<MentoringRoomPostsListResponseDto> allposts = mentoringRoomPostsService.findAllDesc();
        for (MentoringRoomPostsListResponseDto p: allposts) {
            if(Objects.equals(p.getCategory(), MentoringRoomCategory.MENTOR)){
                posts.add(p);
            }
        }
        for (MentoringRoomPostsListResponseDto p: allposts) {
            if(!Objects.equals(p.getCategory(), MentoringRoomCategory.MENTOR)){
                posts.add(p);
            }
        }
        model.addAttribute("allPosts", posts);

        List<Chat> chatList = chatService.findAllChatByRoomId(room_id);

        model.addAttribute("roomId", room_id);
        model.addAttribute("chatList", chatList);
        // 게시물 조회 테스트 코드
//        for (MentoringRoomPostsListResponseDto p: allposts) {
//            System.out.println(p.getCategory());
//            System.out.println(p.getMentoringRoomId());
//            System.out.println(p.getAuthor());
//        }

        MentoringRoom mentoringRoom = mentoringRoomService.findById(room_id);
        model.addAttribute("mentor", mentoringRoom.getUser().getName());

        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());

        return "mentoringRoom";
    }

    @GetMapping("/{room_id}/mentor")
    public String MentoringRoomNoticeForm(@PathVariable Long room_id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }
        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());

        MentoringRoom mentoringRoom = mentoringRoomService.findById(room_id);
        model.addAttribute("mentor", mentoringRoom.getUser().getName());

        List<MentoringRoomPostsListResponseDto> allposts = mentoringRoomPostsService.findAllDesc();
        List<MentoringRoomPostsListResponseDto> posts = new ArrayList<>();
        for (MentoringRoomPostsListResponseDto p: allposts) {
            if(p.getMentoringRoomId() == room_id){
                if(p.getCategory() == MentoringRoomCategory.MENTOR){
                    posts.add(p);
                }
            }
        }
        model.addAttribute("posts", posts);

        return "mentoringRoomMentor";
    }

    @GetMapping("/{room_id}/mentor/post")
    public String MentoringRoomNoticePostForm(@PathVariable Long room_id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }
        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());
        model.addAttribute("post", new MentorSaveRequestDto(user.getName()));

        return "mentoringRoomMentorPost";
    }

    @GetMapping("/{room_id}/mentor/posts/view/{id}")
    public String MentoringRoomNoticeView(@PathVariable Long room_id, @PathVariable Long id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }
        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());

        MentoringRoomPost dto = mentoringRoomPostsService.findById(id);
        model.addAttribute("postId", dto.getId());
        model.addAttribute("post", dto);
        return "mentoringRoomMentorView";
    }

    @GetMapping("/{room_id}/board")
    public String MentoringRoomReferenceForm(@PathVariable Long room_id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }
        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());

        MentoringRoom mentoringRoom = mentoringRoomService.findById(room_id);
        model.addAttribute("mentor", mentoringRoom.getUser().getName());

        List<MentoringRoomPostsListResponseDto> allposts = mentoringRoomPostsService.findAllDesc();
        List<MentoringRoomPostsListResponseDto> posts = new ArrayList<>();
        for (MentoringRoomPostsListResponseDto p: allposts) {
            if(p.getMentoringRoomId() == room_id){
                if(p.getCategory() == MentoringRoomCategory.BOARD){
                    posts.add(p);
                }
            }
        }
        model.addAttribute("posts", posts);

        return "mentoringRoomBoard";
    }

    @GetMapping("/{room_id}/board/post")
    public String MentoringRoomReferencePostForm(@PathVariable Long room_id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }
        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());
        model.addAttribute("post", new MentorSaveRequestDto(user.getName()));

        return "mentoringRoomBoardPost";
    }

    @GetMapping("/{room_id}/board/posts/view/{id}")
    public String MentoringRoomReferenceView(@PathVariable Long room_id, @PathVariable Long id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }
        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());

        MentoringRoomPost dto = mentoringRoomPostsService.findById(id);
        model.addAttribute("postId", dto.getId());
        model.addAttribute("post", dto);

        return "mentoringRoomBoardView";
    }


    @GetMapping("/{room_id}/video")
    public String MentoringRoomVideoForm(@PathVariable Long room_id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }
        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());

        List<MentoringRoomPostsListResponseDto> allposts = mentoringRoomPostsService.findAllDesc();
        List<MentoringRoomPostsListResponseDto> posts = new ArrayList<>();
        for (MentoringRoomPostsListResponseDto p: allposts) {
            if(p.getMentoringRoomId() == room_id){
                if(p.getCategory() == MentoringRoomCategory.VIDEO){
                    posts.add(p);
                }
            }
        }
        model.addAttribute("posts", posts);

        return "mentoringRoomVideo";
    }

    @GetMapping("/{room_id}/video/post")
    public String MentoringRoomVideoePostForm(@PathVariable Long room_id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }
        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());
        model.addAttribute("post", new MentorSaveRequestDto(user.getName()));

        return "mentoringRoomVideoPost";
    }

    @GetMapping("/{room_id}/video/posts/view/{id}")
    public String MentoringRoomVideoView(@PathVariable Long room_id, @PathVariable Long id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }
        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());

        MentoringRoomPost dto = mentoringRoomPostsService.findById(id);
        model.addAttribute("postId", dto.getId());
        model.addAttribute("post", dto);

        return "mentoringRoomVideoView";
    }

    @GetMapping("/{room_id}/update/{id}")
    public String MentoringRoomPostsUpdate(@PathVariable Long room_id, @PathVariable Long id, Model model) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }

        MentoringRoomPost dto = mentoringRoomPostsService.findById(id);
        model.addAttribute("post", dto);
        MentoringRoomPostsUpdateRequestDto update = new MentoringRoomPostsUpdateRequestDto(dto.getTitle(), dto.getContent());
        model.addAttribute("update", update);

        return "mentoringRoomModifytemp";
    }

    @GetMapping("/{room_id}/delete/{id}")
    public String MentoringRoomPostsDelete(@PathVariable Long room_id, @PathVariable Long id) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }

        mentoringRoomPostsService.delete(id);
        return "redirect:/mentoring/room/{room_id}";
    }

}
