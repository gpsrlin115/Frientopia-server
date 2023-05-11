package com.hnu.capstone.controller;


import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.config.UserService;
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

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/signUp")
    public String signUpForm(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail", user.getEmail());
            if (userService.SelectUser(user.getEmail()).getRole() == Role.GUEST){
                return "signUp";
            }
        }

        return "redirect:/";
    }

    @PostMapping("/signUp")
    public String signUp(@RequestParam(value="major") String major,
                         @RequestParam(value="age") int age,
                         @RequestParam(value="gender") String gen,
                         @RequestParam(value="phoneNum") int phoneNum,
                         @RequestParam(value="introduce") String introduce){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        if(sessionUser != null){
            User user = userService.SelectUser(sessionUser.getEmail());
            user.update(gen, age, major, phoneNum, introduce);
            user.roleUpdate(Role.USER);

            userService.UpdateUser(user);
        }

        return "redirect:/";
    }

    @GetMapping("/myPage")
    public String myPageForm(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if(sessionUser != null) {
            User user = userService.SelectUser(sessionUser.getEmail());
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("userGen", user.getGen());
            model.addAttribute("userAge", user.getAge());
            model.addAttribute("userMajor", user.getMajor());
            model.addAttribute("userPhoneNum", user.getPhoneNum());
            model.addAttribute("userIntroduce", user.getIntroduce());
        }
        return "myPage";
    }

    @PostMapping("/myPage")
    public String myPage(@RequestParam(value="major") String major,
                         @RequestParam(value="age") int age,
                         @RequestParam(value="phoneNum") int phoneNum,
                         @RequestParam(value="introduce") String introduce){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        if(sessionUser != null){
            User user = userService.SelectUser(sessionUser.getEmail());
            user.update(user.getGen(), age, major, phoneNum, introduce);
            userService.UpdateUser(user);
        }

        return "redirect:/";
    }

    @PostMapping("/myPage/unregister")
    public String deleteUser(HttpServletRequest request){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if(sessionUser != null){
            userService.DeleteUser(sessionUser.getEmail());
            HttpSession session = request.getSession();
            session.invalidate();
        }
        return "redirect:/";
    }



    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
