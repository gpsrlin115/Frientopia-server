package com.hnu.capstone.controller;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.domain.*;
import com.hnu.capstone.service.MentoringMappingService;
import com.hnu.capstone.service.MentoringRoomService;
import com.hnu.capstone.service.PostsService;
import com.hnu.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mentoring")
public class MentoringViewController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    private final UserService userService;
    private final PostsRepository postsRepository;
    private final MentoringMappingService mentoringMappingService;
    private final MentoringMappingRepository mentoringMappingRepository;
    private final MentoringRoomService mentoringRoomService;

    @GetMapping("")
    public String myMentoring(Model model) {
        SessionUser sessionuser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionuser.getEmail());

        List<Posts> posts = new ArrayList<>();
        for (MentoringMapping mentoringMapping: user.getMentoringMappings()) {
            posts.add(mentoringMapping.getPost());
        }
        model.addAttribute("myMentoring", posts);

        for (Posts p: posts) {
            System.out.println(p.getMentoringRoom().getId());
            System.out.println(p.getTitle());
            System.out.println(p.getContent());
            System.out.println(p.getId());
            System.out.println(p.getUser().getEmail());
        }

        return "mentoring";
    }
}
