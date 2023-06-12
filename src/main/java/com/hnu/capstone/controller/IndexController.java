package com.hnu.capstone.controller;


import com.amazonaws.services.s3.AmazonS3Client;
import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.dto.PostsListResponseDto;
import com.hnu.capstone.dto.PostsSaveRequestDto;
import com.hnu.capstone.dto.PostsUpdateRequestDto;
import com.hnu.capstone.service.MentoringMappingService;
import com.hnu.capstone.service.PayService;
import com.hnu.capstone.service.UserService;
import com.hnu.capstone.domain.*;
import com.hnu.capstone.dto.PostsResponseDto;
import com.hnu.capstone.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    private final UserService userService;
    private final PostsRepository postsRepository;
    private final MentoringMappingService mentoringMappingService;
    private final PayService payService;
    private final AmazonS3Client amazonS3Client;

    // api key 갖고오기
    @Value("#{config['api.key']}")
    private String apiKey;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userRole", userService.SelectUser(user.getEmail()).getRole().name());
        }
        return "index";
    }

    @GetMapping("/mentor-find")
    public String mentorFind(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userRole", userService.SelectUser(user.getEmail()).getRole().name());
        }
        List<PostsListResponseDto> posts = postsService.findAllDesc();
        model.addAttribute("posts", posts);
        // CloserFilter 가 켜졌는지 check 1:켜짐 0:꺼짐
        model.addAttribute("onCloser", 0);
        return "mentor-find";
    }

    @PostMapping("/mentor-find/closer")
    public String mentorFindCloser(@RequestParam(value="latitude") double latitude,
                                   @RequestParam(value="longitude") double longitude,
                                   Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userRole", userService.SelectUser(user.getEmail()).getRole().name());
        }

        // 유저의 현재 정보를 바탕으로 근거리의 멘토링 포스트만 필터링, 거리는 m 단위 (반경 10km 결과만 검색)
        List<PostsListResponseDto> posts = postsService.filterPostsByDistance(latitude, longitude, 10000);
        model.addAttribute("posts", posts);
        // CloserFilter 가 켜졌는지 check 1:켜짐 0:꺼짐
        model.addAttribute("onCloser", 1);
        return "mentor-find";
    }


    @GetMapping("/signUp")
    public String signUpForm(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("userRole", userService.SelectUser(user.getEmail()).getRole().name());
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
                         @RequestParam(value="phoneNum") String phoneNum,
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
        //model.addAttribute("posts", postsService.findAllDesc());
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userService.SelectUser(sessionUser.getEmail());
        model.addAttribute("userName", user.getName());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("userGen", user.getGen());
        model.addAttribute("userAge", user.getAge());
        model.addAttribute("userMajor", user.getMajor());
        model.addAttribute("userPhoneNum", user.getPhoneNum());
        model.addAttribute("userIntroduce", user.getIntroduce());
        model.addAttribute("userRole", user.getRole().name());
        model.addAttribute("userPoint", user.getPoint());
        Payment payment = new Payment();
        List<Payment> payments = payService.findByUserEmail(user.getEmail());
        for (Payment p : payments) {
            payment = p;
        }
        model.addAttribute("impUID", payment.getImpUID());
        model.addAttribute("impUIDAmount", payment.getAmount());
        return "myPage";
    }

    @PostMapping("/myPage")
    public String myPage(@RequestParam(value="major") String major,
                         @RequestParam(value="age") int age,
                         @RequestParam(value="phoneNum") String phoneNum,
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

    @GetMapping("/admin")
    public String adminForm(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("userRole", userService.SelectUser(user.getEmail()).getRole().name());
        }
        List<User> users = userService.SelectAll();
        model.addAttribute("users", users);
        return "admin";
    }



    @GetMapping("/mentor-find/post")
    public String postsSave(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("post", new PostsSaveRequestDto(user.getName()));
            model.addAttribute("userName", user.getName());
            model.addAttribute("userRole", userService.SelectUser(user.getEmail()).getRole().name());
        }
        model.addAttribute("apiKey", apiKey);
        return "post";
    }

    @GetMapping("/posts/view/{id}")
    public String postsView(@PathVariable Long id, Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userRole", userService.SelectUser(user.getEmail()).getRole().name());
            model.addAttribute("userEmail", user.getEmail());
        }
        ClassPathResource resource = new ClassPathResource("post_upload/");
        System.out.println(resource);
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("fileStore", resource);
        model.addAttribute("postId", dto.getId());
        model.addAttribute("post", dto);
        model.addAttribute("postAuthorEmail", postsRepository.findById(id).get().getUser().getEmail());
        model.addAttribute("apiKey", apiKey);

        // post와 user에 맞는 MentoringMapping을 찾고 존재하면 isApply:1 아니면 isApply:0
        if(mentoringMappingService.SelectByPostAndUser(postsRepository.findById(id).get(), userService.SelectUser(user.getEmail())) != null){
            model.addAttribute("isApply", 1);
        }else{
            model.addAttribute("isApply", 0);
        }
        model.addAttribute("s3file", amazonS3Client.getUrl("frientopia", "post_upload/"+dto.getFileName()));

        model.addAttribute("menteeNum", postsRepository.findById(id).get().getMentoringMappings().size() - 1);

        return "postview";
    }

    // 다른 사용자가 주소로 직접 접근해도 수정이 됨. (나중에 고칠 예정)
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("userRole", userService.SelectUser(user.getEmail()).getRole().name());
        }
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        PostsUpdateRequestDto update = new PostsUpdateRequestDto(dto.getTitle(), dto.getContent());
        model.addAttribute("update", update);

        return "modifytemp";
    }

    @GetMapping("/posts/delete/{id}")
    public String postsDelete(@PathVariable Long id) {
        postsService.delete(id);
        return "redirect:/mentor-find";
    }

    @GetMapping("/compiler")
    public String view() {
        return "compiler";
    }
}
