package com.mppkvvclbot.dictionary.repositories;

import com.mppkvvclbot.dictionary.beans.mongo.Word;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by NITISH on 19-01-2017.
 */
@Repository
public interface WordRepository extends MongoRepository<Word,String> {
    public Word findByWord(String word);
}
