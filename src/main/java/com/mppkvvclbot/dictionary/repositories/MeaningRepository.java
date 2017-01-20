package com.mppkvvclbot.dictionary.repositories;

import com.mppkvvclbot.dictionary.beans.meaning.Meaning;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NITISH on 19-01-2017.
 */
@Repository
public interface MeaningRepository extends MongoRepository<Meaning,String> {
    List<Meaning> findByWordId(String wordId);
}
