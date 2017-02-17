package com.mppkvvclbot.dictionary.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mppkvvclbot.dictionary.beans.facebook.*;
import com.mppkvvclbot.dictionary.beans.meaning.Meaning;
import com.mppkvvclbot.dictionary.beans.mongo.Word;
import com.mppkvvclbot.dictionary.repositories.MeaningRepository;
import com.mppkvvclbot.dictionary.repositories.WordRepository;
import com.mppkvvclbot.dictionary.workers.MeaningWorker;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.repository.init.ResourceReader.Type.JSON;

/**
 * Created by NITISH on 19-01-2017.
 */
@Service
public class DictionaryService{

    private static final Logger logger = LoggerFactory.getLogger(DictionaryService.class);

    private static final OkHttpClient client = new OkHttpClient();

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

    public void fetchMeaningForTest(Payload payload){
        logger.info("Queuing payload for test");
        MeaningWorker meaningWorker = new MeaningWorker(this,wordRepository,meaningRepository,payload,DICTIONARY_API);
        logger.info("Starting new worker thread for test dictionary task");
        Thread thread = new Thread(meaningWorker);
        thread.start();
        logger.info("Test Worker thread started successfully sending response to facebook..");
    }

    public void sendMeaning(Payload payload,Word word,List<Meaning> meanings){
        logger.info("Sending Meaning to user");
        if(payload != null && word != null && meanings != null){
            Sender sender = payload.getEntry().get(0).getMessaging().get(0).getSender();
            if(sender != null){
                ReplyPayload replyPayload = new ReplyPayload();
                replyPayload.setRecipient(sender);
                String reply = getReplyText(meanings,word.getWord());
                if(reply.length() > 320){
                    logger.info("Reply length is greater than 320.Trimming to 320 :( ");
                    List<String> replies = makeReplies(reply);
                    int i = 1;
                    if(replies != null && replies.size() > 0){
                        logger.info("Successfully made "+replies.size()+" replies. Looping now...");
                        for(String r : replies){
                            ReplyMessage message = new ReplyMessage();
                            message.setText(r);
                            replyPayload.setMessage(message);
                            logger.info("Calling reply method to finally send the "+ (i++) +" reply");
                            reply(replyPayload);
                        }
                    }else{
                        logger.info("Something went wrong while making replies. Sending the first chunk only");
                        // Splitting reply in parts and sending all the parts.
                        String firtReply = reply.substring(0,320);
                        ReplyMessage firstMessage = new ReplyMessage();
                        firstMessage.setText(firtReply);
                        replyPayload.setMessage(firstMessage);
                        logger.info("Calling reply method to finally send the first reply after failing in making replies");
                        reply(replyPayload);
                    }
                }else {
                    logger.info("Reply length is less than 320");
                    ReplyMessage message = new ReplyMessage();
                    message.setText(reply);
                    replyPayload.setMessage(message);
                    //Calling reply method to finally send the reply to user
                    logger.info("Calling reply method to finally send the reply");
                    reply(replyPayload);
                }
            }
        }
    }

    public void sendErrorMessage(Payload payload,String reply){
        logger.info("Sending Error Message to user");
        if(payload != null && reply != null){
            Sender sender = payload.getEntry().get(0).getMessaging().get(0).getSender();
            if(sender != null){
                ReplyPayload replyPayload = new ReplyPayload();
                replyPayload.setRecipient(sender);
                if(reply.length() > 320){
                    logger.info("Reply length is greater than 320.Trimming to 320 :( ");
                    reply = reply.substring(0,320);
                }
                ReplyMessage message = new ReplyMessage();
                message.setText(reply);
                replyPayload.setMessage(message);
                logger.info("Sending Error message using reply method");
                reply(replyPayload);
                //logger.info("Sending payload as: "+replyPayload);
                /*ObjectMapper mapper = new ObjectMapper();
                String json = "";
                try {
                    json = mapper.writeValueAsString(replyPayload);
                    //System.out.println(json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                try {
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(REPLY_API).newBuilder();
                    urlBuilder.addQueryParameter("access_token", PAGE_ACCESS_TOKEN);
                    String url = urlBuilder.build().toString();
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
                    *//*logger.info("Sending body as");
                    logger.info(""+body.contentLength());*//*
                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    response.close();
                    logger.info(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.info("Unable to reply error to facebook. Maybe test !!");
                }*/
            }

        }
    }

    public String getReplyText(List<Meaning> meanings,String word){
        String reply = "";
        if(meanings != null && meanings.size() > 0){
            logger.info("Forming Single Text Reply from meanings on Thread: "+Thread.currentThread().getName());
            StringBuffer stringBuffer = new StringBuffer();
            int counter = 1;
            stringBuffer.append(word+" means :");
            stringBuffer.append(System.lineSeparator());
            for(Meaning meaning : meanings){
                stringBuffer.append(counter+++". ");
                stringBuffer.append(meaning.getText().trim());
                stringBuffer.append(System.lineSeparator());
            }
            reply = stringBuffer.toString().trim();
        }
        return reply;
    }

    public void reply(ReplyPayload replyPayload){
        logger.info("Inside reply method ");
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(replyPayload);
            // System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(REPLY_API).newBuilder();
            urlBuilder.addQueryParameter("access_token", PAGE_ACCESS_TOKEN);
            String url = urlBuilder.build().toString();
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            response.close();
            logger.info(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("Unable to reply to facebook. Maybe test !!");
        }
    }

    public List<String> makeReplies(String reply){
        List<String> replies = new ArrayList<>();
        int startIndex = 0;
        int endIndex = 320;
        while(startIndex >= reply.length()){
            try {
                String temp = reply.substring(startIndex, endIndex);
                replies.add(temp);
                startIndex = endIndex;
                endIndex = endIndex + 320;
            }catch (IndexOutOfBoundsException ioobe){
                logger.info("Index out of bound exception while making reply for start "+startIndex+" end "+endIndex+" string length: "+reply.length());
                replies.add(reply.substring(startIndex));
                break;
            }
        }
        return replies;
    }
}
