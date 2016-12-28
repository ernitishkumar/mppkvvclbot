package com.mppkvvclbot.dictionary.mongo.services;

import com.mppkvvclbot.dictionary.mongo.beans.MongoUser;
import com.mppkvvclbot.dictionary.mongo.repositories.MongoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by NITISH on 28-12-2016.
 */
@Service
public class MongoUserService {
    @Autowired
    private MongoUserRepository mongoUserRepository;

    public List<MongoUser> getAll(){
        return mongoUserRepository.findAll();
    }
}
