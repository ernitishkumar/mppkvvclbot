package com.mppkvvclbot.dictionary.services;

import com.mppkvvclbot.dictionary.beans.DictionaryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Created by NITISH on 19-01-2017.
 */
@Service
public class DictionaryService {

    @Value("${DICTIONARY_API}")
    private String DICTIONARY_API = "";

    @Value("${PROXY_HOST}")
    private String PROXY_HOST = "";

    @Value("${PROXY_PORT}")
    private int PROXY_PORT = 0;

    public String fetchMeaning(String word){
        System.out.println("Fetching meaning of "+word+" from: "+DICTIONARY_API);
        RestTemplate restTemplate = new RestTemplate();
        DictionaryResponse dictionaryResponse = restTemplate.getForObject(DICTIONARY_API+word, DictionaryResponse.class);
        System.out.println("Recieved Meaning is: ");
        System.out.println(dictionaryResponse);
        return "";
    }
}
