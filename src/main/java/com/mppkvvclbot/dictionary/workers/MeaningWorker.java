package com.mppkvvclbot.dictionary.workers;

import com.mppkvvclbot.dictionary.beans.Payload;
import com.mppkvvclbot.dictionary.services.DictionaryService;
import com.sun.glass.ui.SystemClipboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by NITISH on 19-01-2017.
 */
public class MeaningWorker implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MeaningWorker.class);

    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public MeaningWorker(Payload payload) {
        this.payload = payload;
    }

    @Override
    public void run() {
        logger.info("Thread started running..."+Thread.currentThread().getName());
        try {
            String word = payload.getEntry().get(0).getMessaging().get(0).getMessage().getText();
            logger.info("word = " + word);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.info("InterruptedException in class: MeaningWorker");
            e.printStackTrace();
        }
        logger.info("Fetched Meaning sending back to sender");
    }
}
