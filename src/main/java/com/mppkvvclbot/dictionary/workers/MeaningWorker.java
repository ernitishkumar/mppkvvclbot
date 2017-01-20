package com.mppkvvclbot.dictionary.workers;

import com.mppkvvclbot.dictionary.beans.meaning.DictionaryResponse;
import com.mppkvvclbot.dictionary.beans.facebook.Payload;
import com.mppkvvclbot.dictionary.services.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by NITISH on 19-01-2017.
 */
public class MeaningWorker implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MeaningWorker.class);

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

    public MeaningWorker(Payload payload, String dictionaryUrl) {
        this.payload = payload;
        this.dictionaryUrl = dictionaryUrl;
    }

    @Override
    public void run() {
        try {
            String word = payload.getEntry().get(0).getMessaging().get(0).getMessage().getText();
            logger.info("Fetching meaning of "+word+" from: "+dictionaryUrl);
            RestTemplate restTemplate = new RestTemplate();
            DictionaryResponse dictionaryResponse = restTemplate.getForObject(dictionaryUrl+word, DictionaryResponse.class);
            logger.info("Recieved Meaning is: ");
            logger.info(dictionaryResponse.toString());
        } catch (Exception e) {
            logger.info("Exception in class: MeaningWorker");
            e.printStackTrace();
        }
        logger.info("Fetched Meaning sending back to sender");
    }
}
