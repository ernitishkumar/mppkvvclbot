package com.mppkvvclbot.dictionary.services;

import com.mppkvvclbot.dictionary.beans.facebook.Entry;
import com.mppkvvclbot.dictionary.beans.facebook.Payload;
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

    public void fetchMeaning(Payload payload){
        logger.info("Queuing payload");
        if(payload != null){
            Entry firstEntry = payload.getEntry().get(0);
            if(firstEntry != null){
                logger.info("First Entry is: "+firstEntry.toString());
                String word = firstEntry.getMessaging().get(0).getMessage().getText();
                logger.info("Recieved word from fb is: "+word);
                Word wordInDatabase = wordRepository.findByWord(word);
                if(wordInDatabase != null){
                    logger.info(wordInDatabase.getWord()+" is present in db. Fetching meaning from db");
                    List<Meaning> meaningList = meaningRepository.findByWordId(wordInDatabase.getId());
                }else{
                    logger.info(word+" is not present in db. Fetching meaning from web");
                    MeaningWorker meaningWorker = new MeaningWorker(payload,DICTIONARY_API);
                    Thread thread = new Thread(meaningWorker);
                    thread.start();
                }
            }
        }
    }
}
