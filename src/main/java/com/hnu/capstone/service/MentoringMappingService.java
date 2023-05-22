package com.hnu.capstone.service;

import com.hnu.capstone.domain.MentoringMapping;
import com.hnu.capstone.domain.MentoringMappingRepository;
import com.hnu.capstone.domain.Posts;
import com.hnu.capstone.domain.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MentoringMappingService {
    private final MentoringMappingRepository mentoringMappingRepository;
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
        if(SelectByPostAndUser(post, user) == null){
            mentoringMapping.PostAddMentoringMapping(post);
            mentoringMapping.MentoringMappingAddUser(user);
            mentoringMappingRepository.save(mentoringMapping);
        }
    }

    @Transactional
    public void Delete(Posts post, User user){
        MentoringMapping mentoringMapping = this.SelectByPostAndUser(post,user);
        Long id = mentoringMapping.getId();
        mentoringMappingRepository.deleteById(id);
    }
}
