package com.mppkvvclbot.dictionary.workers;

import com.mppkvvclbot.dictionary.beans.facebook.Entry;
import com.mppkvvclbot.dictionary.beans.facebook.RecievedMessage;
import com.mppkvvclbot.dictionary.beans.meaning.DictionaryResponse;
import com.mppkvvclbot.dictionary.beans.facebook.Payload;
import com.mppkvvclbot.dictionary.beans.meaning.DictionaryTuc;
import com.mppkvvclbot.dictionary.beans.meaning.Meaning;
import com.mppkvvclbot.dictionary.beans.mongo.Word;
import com.mppkvvclbot.dictionary.repositories.MeaningRepository;
import com.mppkvvclbot.dictionary.repositories.WordRepository;
import com.mppkvvclbot.dictionary.services.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Dictionary;
import java.util.List;

/**
 * Created by NITISH on 19-01-2017.
 */
public class MeaningWorker implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MeaningWorker.class);

    private WordRepository wordRepository;

    private MeaningRepository meaningRepository;

    private DictionaryService dictionaryService;

    private Payload payload;

    private String dictionaryUrl;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getDictionaryUrl() {
        return dictionaryUrl;
    }

    public void setDictionaryUrl(String dictionaryUrl) {
        this.dictionaryUrl = dictionaryUrl;
    }

    public WordRepository getWordRepository() {
        return wordRepository;
    }

    public void setWordRepository(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public MeaningRepository getMeaningRepository() {
        return meaningRepository;
    }

    public void setMeaningRepository(MeaningRepository meaningRepository) {
        this.meaningRepository = meaningRepository;
    }

    public DictionaryService getDictionaryService() {
        return dictionaryService;
    }

    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public MeaningWorker(DictionaryService dictionaryService,WordRepository wordRepository, MeaningRepository meaningRepository, Payload payload, String dictionaryUrl) {
        this.dictionaryService = dictionaryService;
        this.wordRepository = wordRepository;
        this.meaningRepository = meaningRepository;
        this.payload = payload;
        this.dictionaryUrl = dictionaryUrl;
    }

    @Override
    public void run() {
        logger.info("Dictionary Worker Started Running...with name: " + Thread.currentThread().getName());
        try {
            if (payload != null) {
                Entry firstEntry = payload.getEntry().get(0);
                if (firstEntry != null) {
                    logger.info("First Entry is: " + firstEntry.toString());
                    List<RecievedMessage> recievedMessages = firstEntry.getMessaging();
                    if (recievedMessages != null) {
                        for (RecievedMessage recievedMessage : recievedMessages) {
                            String word = recievedMessage.getMessage().getText().toLowerCase();
                            logger.info("Recieved word from fb is: " + word);
                            if (word != null) {
                                if(word.matches("^[a-zA-Z]*$")){
                                    System.out.println("Word is correct fetching meanings for "+word);
                                    word = word.toLowerCase();
                                    Word wordInDatabase = wordRepository.findByWord(word);
                                    if (wordInDatabase != null) {
                                        logger.info(wordInDatabase.getWord() + " is present in db. Fetching meaning from db");
                                        List<Meaning> meaningList = meaningRepository.findByWordId(wordInDatabase.getId());
                                        if(meaningList != null ){
                                            logger.info("Found "+meaningList.size()+" for word: "+word+" in database");
                                            logger.info("Calling dictionary service to send meaning to user");
                                            dictionaryService.sendMeaning(payload,wordInDatabase,meaningList);
                                        }
                                    } else {
                                        logger.info(word + " is not present in db. Fetching meaning from web");
                                        RestTemplate restTemplate = new RestTemplate();
                                        DictionaryResponse dictionaryResponse = restTemplate.getForObject(dictionaryUrl + word, DictionaryResponse.class);
                                        logger.info("Recieved meaning from web..proceeding..");
                                        if (dictionaryResponse != null) {
                                            List<DictionaryTuc> tucs = dictionaryResponse.getTuc();
                                            if (tucs != null && tucs.size() > 0) {
                                                List<Meaning> meanings = null;
                                                for(DictionaryTuc tuc : tucs){
                                                    if(tuc.getMeanings() != null && tuc.getMeanings().size() > 0){
                                                        logger.info("Found meaning from tuc..assigning & breaking");
                                                        meanings = tuc.getMeanings();
                                                        break;
                                                    }
                                                }
                                                if (meanings != null && meanings.size() > 0) {
                                                    logger.info("Recieved Meaning of word: " + word + " is not null inserting in database");
                                                    Word databaseWord = new Word(word);
                                                    Word insertedWord = wordRepository.save(databaseWord);
                                                    if (insertedWord != null) {
                                                        logger.info(word + " inserted successfully  in database");
                                                        logger.info("Inserting meanings for word: " + insertedWord.getWord() + " with id: " + insertedWord.getId()+" in db.");
                                                        for(Meaning meaning : meanings){
                                                            meaning.setWordId(insertedWord.getId());
                                                            meaningRepository.save(meaning);
                                                        }
                                                        logger.info("Inserted meanings for word: " + insertedWord.getWord() + " with id: " + insertedWord.getId()+" in db.");
                                                        logger.info("Calling dictionary service to send meaning to user");
                                                        dictionaryService.sendMeaning(payload,insertedWord,meanings);
                                                    }
                                                }else{
                                                    String errorReply = "Sorry no meaning found for "+word+".\n Try Again with a new word!!";
                                                    dictionaryService.sendErrorMessage(payload,errorReply);
                                                }
                                            }else{
                                                String errorReply = "Sorry no meaning found for "+word+".\n Try Again with a new word!!";
                                                dictionaryService.sendErrorMessage(payload,errorReply);
                                            }
                                        }
                                    }
                                }else{
                                    String errorReply = "Sorry I was not able to find meaning for "+word+".\n Try Again with correct word!!";
                                    dictionaryService.sendErrorMessage(payload,errorReply);
                                }
                            } else {
                                String errorReply = "Sorry I was not able to find meaning for "+word;
                                dictionaryService.sendErrorMessage(payload,errorReply);
                                logger.info("Recieved word is null");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            String errorReply = "Sorry I was not able to find meaning for. Try Again ! \n Try Sending just the 'word' for getting meaning";
            dictionaryService.sendErrorMessage(payload,errorReply);
            logger.info("Exception in class: MeaningWorker");
            e.printStackTrace();
        }
        logger.info("Dictionary Worker with name: " + Thread.currentThread().getName() + " stopping..");
    }
}
