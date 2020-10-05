package com.inomera.promotionstest.service;

import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.stereotype.Service;

@Service
public class SlackMessageService {
    public void sendSlackMessage(String url,String channel,String userName,String message){
        SlackApi api = new SlackApi(url);
        api.call(new SlackMessage(channel, userName, message));
    }
}