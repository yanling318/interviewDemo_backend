package com.mercury.interview.controller;

import com.mercury.interview.bean.User;
import com.mercury.interview.http.Response;
import com.mercury.interview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Response register(@RequestBody User user){
        System.out.println(user);
        return userService.register(user);
    }
    @GetMapping("/users")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/check-login")
    public Response checkLogin(Authentication authentication){
        return userService.checklogin(authentication);
    }

}
