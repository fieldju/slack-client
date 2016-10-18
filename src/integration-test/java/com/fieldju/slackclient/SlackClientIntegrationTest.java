package com.fieldju.slackclient;

import org.junit.Before;
import org.junit.Test;

public class SlackClientIntegrationTest {

    private SlackClient client;

    @Before
    public void before() {
        String webHookUrl = System.getProperty("webHookUrl");

        if (webHookUrl == null || webHookUrl.equals("")) {
            throw new RuntimeException("You must supply the webHookUrl");
        }

        client = new SlackClient(webHookUrl);
    }

    @Test
    public void testThatMessageGetsSent() {
        // no assertion here, must go manually look at slack channel
        client.sendMessage("Hello");
        client.sendMessageWithEmoji("Hello Again", "custom-name", ":dog:");
    }

}
