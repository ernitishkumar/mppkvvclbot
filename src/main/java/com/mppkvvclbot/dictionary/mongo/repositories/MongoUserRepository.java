package com.mppkvvclbot.dictionary.mongo.repositories;

import com.mppkvvclbot.dictionary.mongo.beans.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by NITISH on 28-12-2016.
 */
public interface MongoUserRepository extends MongoRepository<MongoUser, String> {
}
