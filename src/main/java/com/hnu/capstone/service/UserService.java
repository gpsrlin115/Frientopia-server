package com.hnu.capstone.service;

import com.hnu.capstone.domain.MentoringMapping;
import com.hnu.capstone.domain.User;
import com.hnu.capstone.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepo;
    @Transactional
    public List<User> SelectAll(){
        return userRepo.findAll();
    }

    public User SelectUser(String email){
        return userRepo.findByEmail(email).get();
    }

    public void UpdateUser(User user){userRepo.save(user);}

    @Transactional
    public void DeleteUser(String email){
        userRepo.deleteByEmail(email);
    }

    @Transactional
    public void registerStudy(String email, MentoringMapping mentoringMapping){
        User user = userRepo.findByEmail(email).get();
        user.addRegisteredMentorPost(mentoringMapping);
        userRepo.save(user);
    }

    @Transactional
    public Long unregisterStudy(String email, Long studyIdx) {
        User user = userRepo.findByEmail(email).get();
        user.getRegisteredStudies().stream()
                .filter(a -> a.getRegisteredStudy().getId().equals(studyIdx))
                .forEach(studyMember -> {
                    studyMember.getParticipant().removeRegisteredStudy(studyMember);
                    studyMember.getRegisteredStudy().removeParticipant(studyMember);
                });

        return user.getId();
    }
}
