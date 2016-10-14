package com.fieldju.slackclient;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

public class SlackClient {

    private static final String MESSAGE_TEMPLATE = "{\"username\":\"%s\",\"icon_emoji\":\"%s\",\"text\":\"%s\"}";
    private static final String DEFAULT_USERNAME = "slack-client";
    private static final String DEFAULT_ICON_EMOJI = ":wolf:";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String username = DEFAULT_USERNAME;
    private String iconEmoji = DEFAULT_ICON_EMOJI;

    private final OkHttpClient client;
    private final String webHookUrl;

    public SlackClient(String webHookUrl) {
        this(new OkHttpClient(), webHookUrl);
    }

    public SlackClient(OkHttpClient client, String webHookUrl) {
        this.client = client;
        this.webHookUrl = webHookUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIconEmoji(String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

    public void sendMessage(String message) {
        sendMessage(message, username, iconEmoji);
    }

    public void sendMessage(String message, String userName, String iconEmoji) {
        try {
            client.newCall(
                    new Request.Builder()
                            .url(webHookUrl)
                            .post(RequestBody.create(JSON, String.format(MESSAGE_TEMPLATE, userName, iconEmoji, message)))
                            .build()
            ).execute();
        } catch (IOException e) {
            throw new RuntimeException("Failed to communicate with Slack", e);
        }
    }

}
