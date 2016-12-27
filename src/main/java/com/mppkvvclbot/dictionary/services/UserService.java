package com.mppkvvclbot.dictionary.services;

import com.mppkvvclbot.dictionary.beans.User;
import com.mppkvvclbot.dictionary.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by NITISH on 28-12-2016.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public List<User> getAll(){
        return userRepository.findAll();
    }
}
