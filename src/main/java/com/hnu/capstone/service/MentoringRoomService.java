package com.hnu.capstone.service;

import com.hnu.capstone.domain.MentoringRoom;
import com.hnu.capstone.domain.MentoringRoomRepository;
import com.hnu.capstone.domain.Posts;
import com.hnu.capstone.domain.PostsRepository;
import com.hnu.capstone.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void newMentoringRoom(Long postID) {
        MentoringRoom mentoringRoom = new MentoringRoom(postsRepository.findById(postID).get());
        mentoringRoomRepository.save(mentoringRoom);
    }
}
