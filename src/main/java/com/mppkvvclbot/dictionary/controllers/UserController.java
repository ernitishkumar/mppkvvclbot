package com.mppkvvclbot.dictionary.controllers;

import com.mppkvvclbot.dictionary.beans.User;
import com.mppkvvclbot.dictionary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by NITISH on 28-12-2016.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public List<User> getAll(){
        return userService.getAll();
    }
}
