package com.hnu.capstone.service;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.domain.Posts;
import com.hnu.capstone.domain.PostsRepository;
import com.hnu.capstone.domain.User;
import com.hnu.capstone.domain.UserRepository;
import com.hnu.capstone.dto.PostsResponseDto;
import com.hnu.capstone.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepo;
    private final PostsService postsService;
    private final PostsRepository postsRepository;

    @Transactional
    public List<User> SelectAll(){
        return userRepo.findAll();
    }

    public User SelectUser(String email){
        return userRepo.findByEmail(email).get();
    }

    public void UpdateUser(User user){
        userRepo.save(user);
    }

    @Transactional
    public void DeleteUser(String email){
        userRepo.deleteByEmail(email);
    }

    @Transactional
    public Long UserToPost(SessionUser user, PostsSaveRequestDto requestDto) throws IOException {
        if(user != null) {
            this.SelectUser(user.getEmail()).PostAddUser(requestDto);
        }
        Long post_id = postsService.save(requestDto);
        this.SelectUser(user.getEmail()).UserAddPost(postsRepository.findById(post_id).get());

        return post_id;
    }
}
