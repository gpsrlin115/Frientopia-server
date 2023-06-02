package com.hnu.capstone.service;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.domain.*;
import com.hnu.capstone.dto.PostsSaveRequestDto;
import com.hnu.capstone.dto.mentoringroom.MentorSaveRequestDto;
import com.hnu.capstone.dto.mentoringroom.BoardSaveRequestDto;
import com.hnu.capstone.dto.mentoringroom.VideoSaveRequestDto;
import lombok.RequiredArgsConstructor;
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
    private final MentoringRoomPostsService mentoringRoomPostsService;
    private final MentoringRoomService mentoringRoomService;

    @Transactional
    public List<User> SelectAll(){
        return userRepo.findAll();
    }

    public User SelectUser(String email){
        if(email != null){
            if(userRepo.findByEmail(email).isPresent()){
                return userRepo.findByEmail(email).get();
            }
        }
        return null;
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

    @Transactional
    public Long UserToPost(SessionUser user, MentorSaveRequestDto requestDto, Long roomId) throws IOException {
        if(user != null) {
            this.SelectUser(user.getEmail()).PostAddUser(requestDto);
        }

        Long post_id = mentoringRoomPostsService.save(requestDto);
        mentoringRoomPostsService.findById(post_id).MentoringRoomPostToUser(this.SelectUser(user.getEmail()));
        mentoringRoomPostsService.findById(post_id).MentoringRoomPostToRoom(mentoringRoomService.findById(roomId));

        return post_id;
    }

    @Transactional
    public Long UserToPost(SessionUser user, BoardSaveRequestDto requestDto, Long roomId) throws IOException {
        if(user != null) {
            this.SelectUser(user.getEmail()).PostAddUser(requestDto);
        }
        Long post_id = mentoringRoomPostsService.save(requestDto);
        mentoringRoomPostsService.findById(post_id).MentoringRoomPostToUser(this.SelectUser(user.getEmail()));
        mentoringRoomPostsService.findById(post_id).MentoringRoomPostToRoom(mentoringRoomService.findById(roomId));

        return post_id;
    }

    @Transactional
    public Long UserToPost(SessionUser user, VideoSaveRequestDto requestDto, Long roomId) throws IOException {
        if(user != null) {
            this.SelectUser(user.getEmail()).PostAddUser(requestDto);
        }
        Long post_id = mentoringRoomPostsService.save(requestDto);
        mentoringRoomPostsService.findById(post_id).MentoringRoomPostToUser(this.SelectUser(user.getEmail()));
        mentoringRoomPostsService.findById(post_id).MentoringRoomPostToRoom(mentoringRoomService.findById(roomId));

        return post_id;
    }
}
