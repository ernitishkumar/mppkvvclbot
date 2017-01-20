package com.mppkvvclbot.dictionary.services;

import com.mppkvvclbot.dictionary.beans.facebook.Entry;
import com.mppkvvclbot.dictionary.beans.facebook.Payload;
import com.mppkvvclbot.dictionary.beans.facebook.RecievedMessage;
import com.mppkvvclbot.dictionary.beans.meaning.Meaning;
import com.mppkvvclbot.dictionary.beans.mongo.Word;
import com.mppkvvclbot.dictionary.repositories.MeaningRepository;
import com.mppkvvclbot.dictionary.repositories.WordRepository;
import com.mppkvvclbot.dictionary.workers.MeaningWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by NITISH on 19-01-2017.
 */
@Service
public class DictionaryService{

    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private MeaningRepository meaningRepository;

    @Value("${DICTIONARY_API}")
    private String DICTIONARY_API = "";

    @Value("${API_FOR_REPLY}")
    private String REPLY_API = "";

    public void fetchMeaning(Payload payload){
        logger.info("Queuing payload");
        MeaningWorker meaningWorker = new MeaningWorker(this,wordRepository,meaningRepository,payload,DICTIONARY_API);
        logger.info("Starting new worker thread for dictionary task");
        Thread thread = new Thread(meaningWorker);
        thread.start();
        logger.info("Worker thread started successfully sending response to facebook..");
    }

    public void sendMeaning(Payload payloadd,Word word,List<Meaning> meanings){
        logger.info("Sending Meaning to user");
        if(payloadd != null && word != null && meanings != null){

        }
    }
}
