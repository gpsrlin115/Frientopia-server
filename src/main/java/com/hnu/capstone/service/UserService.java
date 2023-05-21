package com.hnu.capstone.service;

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
}
