package com.mppkvvclbot.dictionary.controllers;

import com.mppkvvclbot.dictionary.beans.Payload;
import com.mppkvvclbot.dictionary.services.DictionaryService;
import org.jcp.xml.dsig.internal.SignerOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Hp on 29-12-2016.
 */
@Configuration
@RequestMapping(value = "/facebook")
public class WebhookController {

    @Value("${VERIFY_TOKEN}")
    private String VERIFY_TOKEN = "";

    @Value("${PAGE_ACCESS_TOKEN}")
    private String PAGE_ACCESS_TOKEN = "";

    @Value("${DICTIONARY_API}")
    private String DICTIONARY_API = "";

    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "/webhook",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity validateConnection(@RequestParam("hub.mode")String hubMode, @RequestParam("hub.verify_token")String hubToken, @RequestParam("hub.challenge")String hubChallenge){
        System.out.println("Validating webhook connection: ");
        System.out.println("Recieved hub.mode is: "+hubMode);
        System.out.println("Recieved hub.verify_token is: "+hubToken);
        ResponseEntity<String> responseEnity = null;
        if(hubMode != null && hubMode.equalsIgnoreCase("subscribe")){
            System.out.println("Verifying with token: "+VERIFY_TOKEN);
            if(hubToken != null && hubToken.equals(VERIFY_TOKEN)){
                System.out.println("Webhook verified successfully");
                responseEnity = new ResponseEntity(hubChallenge,HttpStatus.OK);
            }else{
                System.out.println("Verify Token is null");
            }
        }else{
            System.out.println("Hub mode is null");
        }
        return responseEnity;
    }

    @RequestMapping(value = "/webhook",method = RequestMethod.POST,consumes = "application/json")
    @ResponseBody
    public ResponseEntity recieveMessage(@RequestBody Payload payload){
        System.out.println("POST Webhook started by facebook");
        dictionaryService.fetchMeaning("cricket");
        return new ResponseEntity("Recieved Fine",HttpStatus.OK);
    }
}
