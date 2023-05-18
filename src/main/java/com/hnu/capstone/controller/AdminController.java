package com.hnu.capstone.controller;

import com.hnu.capstone.config.SessionUser;
import com.hnu.capstone.domain.Role;
import com.hnu.capstone.domain.User;
import com.hnu.capstone.service.PostsService;
import com.hnu.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    @Autowired
    UserService userService;

    @GetMapping("/admin/{email}")
    public String adminUser(@PathVariable("email") String email, Model model){
        User user = userService.SelectUser(email);
        model.addAttribute(user);
        model.addAttribute("roles", Role.values());
        return "adminUser";
    }

    @PostMapping("/admin/{email}")
    public String adminUserModify(@PathVariable("email") String email,
                                  @RequestParam(value="major") String major,
                                  @RequestParam(value="age") int age,
                                  @RequestParam(value="phoneNum") int phoneNum,
                                  @RequestParam(value="introduce") String introduce,
                                  @RequestParam(value="gender") String gen,
                                  @RequestParam(value="role") Role role){

        User user = userService.SelectUser(email);
        user.update(gen, age, major, phoneNum, introduce);
        user.roleUpdate(role);
        userService.UpdateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/{email}/delete")
    public String adminUserDelete(@PathVariable("email") String email){
        /*회원 세션 종료 구현 필요*/
        userService.DeleteUser(email);
        return "redirect:/admin";
    }
}
