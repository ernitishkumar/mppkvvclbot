package com.mppkvvclbot.dictionary.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hp on 29-12-2016.
 */
@Configuration
@RestController
public class WebhookController {

    @Value("$VERIFY_TOKEN")
    private String VERIFY_TOKEN = "";

    @RequestMapping(value = "/webhook",method = RequestMethod.GET)
    public String validateConnection(@RequestParam("hub.mode")String hubMode,@RequestParam("hub.verify_token")String hubToken,@RequestParam("hub.challenge")String hubChallenge){
        System.out.println("Validating webhook connection: ");
        System.out.println("Recieved hub.mode is: "+hubMode);
        System.out.println("Recieved hub.verify_token is: "+hubToken);
        if(hubMode != null && hubMode.equalsIgnoreCase("subscribe")){
            if(hubToken != null && hubToken.equals(VERIFY_TOKEN)){
                System.out.println("Webhook verified successfully");
                return hubChallenge;
            }else{
                System.out.println("Verify Token is null");
            }
        }else{
            System.out.println("Hub mode is null");
        }
        return null;
    }
}
