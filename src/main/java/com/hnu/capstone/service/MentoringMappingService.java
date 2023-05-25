package com.hnu.capstone.service;

import com.hnu.capstone.domain.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MentoringMappingService {
    private final MentoringMappingRepository mentoringMappingRepository;
    private final MentoringRoomService mentoringRoomService;

    @Transactional
    public List<MentoringMapping> SelectAll(){
        return mentoringMappingRepository.findAll();
    }

    @Transactional
    public MentoringMapping SelectByPosts(Posts post){
        return mentoringMappingRepository.findAllByPost(post).get();
    }

    @Transactional
    public MentoringMapping SelectByUser(User user){
        return mentoringMappingRepository.findAllByUser(user).get();
    }

    @Transactional
    public MentoringMapping SelectByPostAndUser(Posts post, User user){
        return mentoringMappingRepository.findByPostAndUser(post,user);
    }

    @Transactional
    public void save(Posts post, User user){
        MentoringMapping mentoringMapping = new MentoringMapping(post, user);
        if(post != null && user !=null){
            if(SelectByPostAndUser(post, user) == null){
                mentoringMapping.PostAddMentoringMapping(post);
                mentoringMapping.MentoringMappingAddUser(user);
                mentoringMappingRepository.save(mentoringMapping);
            }
        }
    }

    @Transactional
    public void updateMentoringRoom(MentoringMapping mentoringMapping){
        MentoringRoom mentoringRoom = mentoringRoomService.findByPost(mentoringMapping.getPost());
        mentoringMapping.setMentoringRoom(mentoringRoom);
        mentoringMappingRepository.save(mentoringMapping);
    }

    @Transactional
    public void Delete(Posts post, User user){
        MentoringMapping mentoringMapping = this.SelectByPostAndUser(post,user);
        Long id = mentoringMapping.getId();
        mentoringMappingRepository.deleteById(id);
    }
}
