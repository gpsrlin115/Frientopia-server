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
    public List<MentoringMapping> SelectAllByPosts(Posts posts){
        return mentoringMappingRepository.findAllByPost(posts);
    }

    @Transactional
    public MentoringMapping SelectAllByUser(User user){
        return mentoringMappingRepository.findAllByUser(user).get();
    }

    @Transactional
    public MentoringMapping SelectByPostAndUser(Posts post, User user){
        return mentoringMappingRepository.findByPostAndUser(post,user);
    }

    @Transactional
    public Long save(Posts post, User user){
        if(post != null && user != null){
            MentoringMapping mentoringMapping = new MentoringMapping(post, user);
            if(SelectByPostAndUser(post, user) == null){
                mentoringMapping.PostAddMentoringMapping(post);
                mentoringMapping.UserAddMentoringMapping(user);
                mentoringMappingRepository.save(mentoringMapping);

                return mentoringMappingRepository.findByPostAndUser(post, user).getId();
            }
            return mentoringMappingRepository.findByPostAndUser(post, user).getId();
        }
        return null;
    }

    @Transactional
    public Long updateMentoringRoom(Posts post, User user){
        if(post != null && user !=null){
            if(SelectByPostAndUser(post, user) != null){
                MentoringMapping mentoringMapping = SelectByPostAndUser(post, user);
                MentoringRoom mentoringRoom = mentoringRoomService.findByPost(mentoringMapping.getPost());
                mentoringMapping.setMentoringRoom(mentoringRoom);

                return mentoringMapping.getId();
            }
            return null;
        }
        return null;
    }

    @Transactional
    public void Delete(Posts post, User user){
        MentoringMapping mentoringMapping = this.SelectByPostAndUser(post,user);
        Long id = mentoringMapping.getId();
        mentoringMappingRepository.deleteById(id);
    }
}
