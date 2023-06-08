package com.hnu.capstone.controller;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.domain.*;
import com.hnu.capstone.dto.*;
import com.hnu.capstone.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    private final UserService userService;
    private final PostsRepository postsRepository;
    private final MentoringMappingService mentoringMappingService;
    private final MentoringMappingRepository mentoringMappingRepository;
    private final MentoringRoomService mentoringRoomService;
    private final ChatService chatService;


    @PostMapping("/api/v1/posts")
    public Long save(@ModelAttribute PostsSaveRequestDto requestDto) throws IOException {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        Long post_num = userService.UserToPost(user, requestDto);
        mentoringRoomService.newMentoringRoom(post_num);

        User mentor = userService.SelectUser(user.getEmail());

        Posts post;
        if(postsRepository.findById(post_num).isPresent()){
            post = postsRepository.findById(post_num).get();
        }else{
            post = null;
        }

        System.out.println(requestDto.getLatitude());

        mentoringMappingService.save(post, mentor);
        mentoringMappingService.updateMentoringRoom(post, mentor);

        chatService.createRoom(mentor.getName());

        return post_num;
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @ModelAttribute PostsUpdateRequestDto requestDto) {
        if (requestDto.getTitle() == null){
            return 0L;
        }else{
            return postsService.update(id, requestDto);
        }
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @PostMapping("/api/v1/posts/{id}")
    public Long postMapping(@PathVariable Long id, @ModelAttribute PostsUpdateRequestDto requestDto){
        return id;
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id)
    {
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts/list")
    public List<PostsListResponseDto> findAll() {
        return postsService.findAllDesc();
    }

    @GetMapping("/api/v1/posts/mapping/{post_id}")
    public String mapping(@PathVariable Long post_id){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        User user = userService.SelectUser(sessionUser.getEmail());
        Posts post = new Posts();
        if(postsRepository.findById(post_id).isPresent()){
            post = postsRepository.findById(post_id).get();
        }else{
            post = null;
        }

        Long mm = mentoringMappingService.save(post, user);
        Long um = mentoringMappingService.updateMentoringRoom(post, user);

        if (mm != null && um != null){
            return "신청완료";
        }
        return "신청실패";
    }

    @GetMapping("/user/posts")
    public String userPosts(){
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");

        User user = userService.SelectUser(sessionUser.getEmail());
        int post_num = user.getPosts().size();

        List<String> posts = new ArrayList<>();
        for (Posts post: user.getPosts()) {
            posts.add(post.getTitle());
        }

        return posts.toString();
    }
}
