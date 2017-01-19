package com.mppkvvclbot.dictionary.services;

import com.mppkvvclbot.dictionary.beans.DictionaryResponse;
import com.mppkvvclbot.dictionary.beans.Payload;
import com.mppkvvclbot.dictionary.workers.MeaningWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Created by NITISH on 19-01-2017.
 */
@Service
public class DictionaryService {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);

    @Value("${DICTIONARY_API}")
    private String DICTIONARY_API = "";

    public String fetchMeaning(String word){
        logger.info("Fetching meaning of "+word+" from: "+DICTIONARY_API);
        RestTemplate restTemplate = new RestTemplate();
        DictionaryResponse dictionaryResponse = restTemplate.getForObject(DICTIONARY_API+word, DictionaryResponse.class);
        logger.info("Recieved Meaning is: ");
        logger.info(dictionaryResponse.toString());
        return "";
    }

    public void fetchMeaning(Payload payload){
        logger.info("Queuing payload");
        MeaningWorker meaningWorker = new MeaningWorker(payload);
        Thread thread = new Thread(meaningWorker);
        thread.start();
    }
}
