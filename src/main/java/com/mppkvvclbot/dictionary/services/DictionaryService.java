package com.mppkvvclbot.dictionary.services;

import com.mppkvvclbot.dictionary.beans.facebook.*;
import com.mppkvvclbot.dictionary.beans.meaning.Meaning;
import com.mppkvvclbot.dictionary.beans.mongo.Word;
import com.mppkvvclbot.dictionary.repositories.MeaningRepository;
import com.mppkvvclbot.dictionary.repositories.WordRepository;
import com.mppkvvclbot.dictionary.workers.MeaningWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
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

    @Value("${PAGE_ACCESS_TOKEN}")
    private String PAGE_ACCESS_TOKEN = "";

    public void fetchMeaning(Payload payload){
        logger.info("Queuing payload");
        MeaningWorker meaningWorker = new MeaningWorker(this,wordRepository,meaningRepository,payload,DICTIONARY_API);
        logger.info("Starting new worker thread for dictionary task");
        Thread thread = new Thread(meaningWorker);
        thread.start();
        logger.info("Worker thread started successfully sending response to facebook..");
    }

    public void sendMeaning(Payload payload,Word word,List<Meaning> meanings){
        logger.info("Sending Meaning to user");
        if(payload != null && word != null && meanings != null){
            Sender sender = payload.getEntry().get(0).getMessaging().get(0).getSender();
            if(sender != null){
                ReplyPayload replyPayload = new ReplyPayload();
                replyPayload.setRecipient(sender);
                String reply = getReplyText(meanings);
                if(reply.length() > 320){
                    logger.info("Reply length is greater than 320.Trimming to 320 :( ");
                    reply = reply.substring(0,320);
                }
                logger.info("Sending Reply from meaning as: \n"+reply);
                Message message = new Message();
                message.setText(reply);
                replyPayload.setMessage(message);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<ReplyPayload> httpEntity = new HttpEntity<ReplyPayload>(replyPayload);

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.postForObject(REPLY_API+PAGE_ACCESS_TOKEN,httpEntity,ReplyPayload.class);
            }

        }
    }

    public String getReplyText(List<Meaning> meanings){
        String reply = "";
        if(meanings != null && meanings.size() > 0){
            logger.info("Forming Single Text Reply from meanings on Thread: "+Thread.currentThread().getName());
            StringBuffer stringBuffer = new StringBuffer();
            int counter = 1;
            for(Meaning meaning : meanings){
                stringBuffer.append(counter+++". ");
                stringBuffer.append(meaning.getText().trim());
                stringBuffer.append(System.lineSeparator());
            }
            reply = stringBuffer.toString().trim();
        }
        return reply;
    }
}
