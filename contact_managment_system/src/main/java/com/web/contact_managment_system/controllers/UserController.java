package com.web.contact_managment_system.controllers;

import com.web.contact_managment_system.models.User;
import com.web.contact_managment_system.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    UserService userService;
    PasswordEncoder passwordEncoder;
    public UserController(UserService userService,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder=passwordEncoder;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @PostMapping("/register")
    public String register(@RequestParam("username")String username,@RequestParam("password")String password) {
        Optional<User> user = userService.findByUsername(username);
        System.out.println(user);
        if(user.isPresent())
            return "redirect:/user/register?emailError=true";
        User user1=new User(this.passwordEncoder.encode(password),username);
        userService.save(user1);
        return "login";
    }

}
