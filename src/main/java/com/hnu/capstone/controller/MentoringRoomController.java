package com.hnu.capstone.controller;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.dto.mentoringroom.MentoringRoomPostsListResponseDto;
import com.hnu.capstone.dto.mentoringroom.MentoringRoomPostsResponseDto;
import com.hnu.capstone.dto.mentoringroom.MentoringRoomPostsUpdateRequestDto;
import com.hnu.capstone.dto.mentoringroom.NoticeSaveRequestDto;
import com.hnu.capstone.service.*;
import com.hnu.capstone.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/{room_id}")
    public String MentoringRoomForm(@PathVariable Long room_id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id); //enter 성공시 1L, 신청이 안 되어 있을 시 0L, 멘토링 룸이 존재하지 않을 시 -1L, 멘토일 시 2L;
        System.out.println(result != 1L && result != 2L);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }

        List<MentoringRoomPostsListResponseDto> allposts = mentoringRoomPostsService.findAllDesc();
        model.addAttribute("allPosts", allposts);

        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());

        return "mentoringRoom";
    }

    @GetMapping("/{room_id}/notice")
    public String MentoringRoomNoticeForm(@PathVariable Long room_id, Model model){
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
                if(p.getCategory() == MentoringRoomCategory.NOTICE){
                    posts.add(p);
                }
            }
        }
        model.addAttribute("posts", posts);

        return "mentoringRoomNotice";
    }

    @GetMapping("/{room_id}/notice/post")
    public String MentoringRoomNoticePostForm(@PathVariable Long room_id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }
        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());
        model.addAttribute("post", new NoticeSaveRequestDto(user.getName()));

        return "mentoringRoomNoticePost";
    }

    @GetMapping("/{room_id}/notice/posts/view/{id}")
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

        return "mentoringRoomNoticeView";
    }

    @GetMapping("/{room_id}/reference")
    public String MentoringRoomReferenceForm(@PathVariable Long room_id, Model model){
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
                if(p.getCategory() == MentoringRoomCategory.REFERENCE){
                    posts.add(p);
                }
            }
        }
        model.addAttribute("posts", posts);

        return "mentoringRoomReference";
    }

    @GetMapping("/{room_id}/reference/post")
    public String MentoringRoomReferencePostForm(@PathVariable Long room_id, Model model){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        Long result = mentoringRoomService.mentoringRoomEnter(user, room_id);
        if(result != 1L && result != 2L){
            return "redirect:/mentor-find";
        }
        model.addAttribute("userName", user.getName());
        model.addAttribute("userRole", user.getRole());
        model.addAttribute("post", new NoticeSaveRequestDto(user.getName()));

        return "mentoringRoomReferencePost";
    }

    @GetMapping("/{room_id}/reference/posts/view/{id}")
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

        return "mentoringRoomReferenceView";
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
        model.addAttribute("post", new NoticeSaveRequestDto(user.getName()));

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
