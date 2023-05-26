package com.hnu.capstone.service;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.domain.*;
import com.hnu.capstone.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MentoringRoomService {
    private final MentoringRoomRepository mentoringRoomRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public MentoringRoom findByPost(Posts post){
        return mentoringRoomRepository.findByPost(post).get();
    }

    @Transactional
    public MentoringRoom findById(Long id){
        if (mentoringRoomRepository.findById(id).isPresent()){
            return mentoringRoomRepository.findById(id).get();
        }else{
            return null;
        }
    }

    @Transactional
    public void newMentoringRoom(Long postID) {
        MentoringRoom mentoringRoom = new MentoringRoom(postsRepository.findById(postID).get());
        mentoringRoomRepository.save(mentoringRoom);
    }

    @Transactional
    public Long mentoringRoomEnter(User user, Long room_id){ //enter 성공시 1L, 신청이 안 되어 있을 시 0L, 멘토링 룸이 존재하지 않을 시 null;
        MentoringRoom mentoringRoom = this.findById(room_id);
        if(mentoringRoom != null){
            for (User mentee : mentoringRoom.getMentee() ) {
                if(mentee == user){
                    return 1L;
                }
            }
            return 0L;
        }else {
            return null;
        }
    }
}
