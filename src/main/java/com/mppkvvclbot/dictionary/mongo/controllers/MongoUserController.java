package com.mppkvvclbot.dictionary.mongo.controllers;

import com.mppkvvclbot.dictionary.mongo.beans.MongoUser;
import com.mppkvvclbot.dictionary.mongo.services.MongoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by NITISH on 28-12-2016.
 */
@RestController
public class MongoUserController {
    @Autowired
    private MongoUserService mongoUserService;

    @RequestMapping("/mongo/users")
    public List<MongoUser> getAll(){
        return mongoUserService.getAll();
    }
}
