package com.hnu.capstone.controller;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.dto.PostsListResponseDto;
import com.hnu.capstone.dto.PostsResponseDto;
import com.hnu.capstone.dto.PostsSaveRequestDto;
import com.hnu.capstone.dto.PostsUpdateRequestDto;
import com.hnu.capstone.service.PostsService;
import com.hnu.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    private final UserService userService;

    @PostMapping("/api/v1/posts")
    public Long save(@ModelAttribute PostsSaveRequestDto requestDto){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            requestDto.setUser(userService.SelectUser(user.getEmail()));
        }
        return postsService.save(requestDto);
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
    public PostsResponseDto findById (@PathVariable Long id, Model model)
    {
        return postsService.findById(id);
    }

    @GetMapping("/api/v1/posts/list")
    public List<PostsListResponseDto> findAll() {
        return postsService.findAllDesc();
    }
}
