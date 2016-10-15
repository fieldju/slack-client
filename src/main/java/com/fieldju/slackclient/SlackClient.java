package com.fieldju.slackclient;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

/**
 * A simple client that sends messages to a Slack channel using a Slack Web Hook Url integration.
 */
public class SlackClient {

    private static final String MESSAGE_TEMPLATE = "{\"username\":\"%s\",\"icon_emoji\":\"%s\",\"text\":\"%s\"}";
    private static final String DEFAULT_USERNAME = "slack-client";
    private static final String DEFAULT_ICON_EMOJi = ":wolf:";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String username = DEFAULT_USERNAME;
    private String iconEmoji = DEFAULT_ICON_EMOJi;

    private final OkHttpClient client;
    private final String webHookUrl;

    /**
     * @param webHookUrl The web hook url from Slack.
     */
    public SlackClient(String webHookUrl) {
        this(new OkHttpClient(), webHookUrl);
    }

    /**
     *
     * @param client A custom client.
     * @param webHookUrl The web hook url from Slack.
     */
    public SlackClient(OkHttpClient client, String webHookUrl) {
        this.client = client;
        this.webHookUrl = webHookUrl;
    }

    /**
     * Override the default username for future messages sent to Slack.
     * @param username The username to send messages as for future messages sent.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Override the default username for future messages sent to Slack
     * @param iconEmoji The icon emoji or image url to use as the avatar for new messages sent
     */
    public void setIconEmoji(String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

    /**
     * Send a message to slack using the configured or default username and iconEmoji.
     * @param message The message to send to slack through the web hook.
     */
    public void sendMessage(String message) {
        sendMessage(message, username, iconEmoji);
    }

    /**
     * Send a message to slack customizing username and the iconEmoji
     * @param message The message to send to slack through the web hook.
     * @param userName The username to send the message as.
     * @param iconEmoji The icon emoji or image url to use as the avatar.
     */
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
