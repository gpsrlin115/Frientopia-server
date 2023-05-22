package com.hnu.capstone.controller;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.controller.helper.AuthAttributeAddHelper;
import com.hnu.capstone.domain.MentoringMapping;
import com.hnu.capstone.domain.Posts;
import com.hnu.capstone.domain.User;
import com.hnu.capstone.dto.PostsListResponseDto;
import com.hnu.capstone.dto.PostsResponseDto;
import com.hnu.capstone.dto.PostsSaveRequestDto;
import com.hnu.capstone.dto.PostsUpdateRequestDto;
import com.hnu.capstone.service.MentoringMemberService;
import com.hnu.capstone.service.PostsService;
import com.hnu.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final Long ALREADY_REGISTER_ERROR = -1L;
    private static final Long NO_PERMISSION_ERROR = -2L;
    private final PostsService postsService;
    private final HttpSession httpSession;
    private final UserService userService;
    private final MentoringMemberService mentoringMemberService;

    @PostMapping("/api/v1/posts")
    public Long save(@ModelAttribute PostsSaveRequestDto requestDto){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            requestDto.setUser(userService.SelectUser(user.getEmail()));
        }
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public ModelAndView update(@PathVariable Long id, @ModelAttribute PostsUpdateRequestDto requestDto) {
        if (requestDto.getTitle() == null){
            return new ModelAndView("redirect:/mentor-find");
        }else{
            postsService.update(id, requestDto);
            ModelAndView mav = new ModelAndView("redirect:/mentor-find");
            return mav;
        }
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        postsService.delete(id);
        return new ModelAndView("redirect:/mentor-find");
    }

    @PostMapping("/api/v1/posts/{id}")
    public Long postMapping(@PathVariable Long id, @ModelAttribute PostsUpdateRequestDto requestDto){
        return id;
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id) {

        return postsService.findById(id);
    }


    @GetMapping("/api/v1/posts/list")
    public List<PostsListResponseDto> findAll() {

        return postsService.findAllDesc();
    }
    @PutMapping("/{work}/studies/{email}")
    public Long registerOrUnregister(
            @PathVariable("work") String work, @PathVariable("email") Long id ) {

        if (!AuthAttributeAddHelper.isLoggedIn()) {
            return NO_PERMISSION_ERROR;
        }

        UserDetails user = AuthAttributeAddHelper.getUserDetails();

        if (work.equals("register")) {
            return register(id, userService.SelectUser(email));
        } else {
            return unregister(id, userService.SelectUser());
        }
    }

    private Long register(Long id, String email) {
        PostsResponseDto post = postsService.findById(id);
        User user = userService.SelectUser(email);

        if (!mentoringMemberService.isAlreadyRegister(post, user.getId())) {
            MentoringMapping studyMember = mentoringMemberService.getAfterMapping(post, user);
            userService.registerStudy(email, studyMember);
            postsService.assignParticipant(post.getId(), studyMember);
            return post.getId();
        }

        return ALREADY_REGISTER_ERROR;
    }

    private Long unregister(Long idx, String email) {
        return userService.unregisterStudy(email, idx);
    }
}
