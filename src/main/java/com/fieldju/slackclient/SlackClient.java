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

    private static final String DEFAULT_USERNAME = "slack-client";
    private static final String DEFAULT_ICON_EMOJi = ":wolf:";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String username = DEFAULT_USERNAME;
    private String iconEmoji = DEFAULT_ICON_EMOJi;
    private String iconUrl;
    private String channel;

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
     * Override the default icon emoji for future messages sent to Slack
     * @param iconEmoji The icon emoji to use as the avatar for new messages sent
     */
    public void setIconEmoji(String iconEmoji) {
        this.iconEmoji = iconEmoji;
    }

    /**
     * Override the default icon emoji and use an image url for future messages sent to Slack
     * @param iconUrl The icon url to use as the avatar for new messages sent
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * Send a message to slack using the configured or default username and icon.
     * @param text The message to send to slack through the web hook.
     */
    public void sendMessage(String text) {
        if (iconUrl != null) {
            sendMessageWithIconUrl(text, username, iconUrl, channel);
        } else {
            sendMessageWithEmoji(text, username, iconEmoji, channel);
        }
    }

    /**
     * Send a message to slack customizing username and the icon
     * @param text The message to send to slack through the web hook.
     * @param userName The username to send the message as.
     * @param iconUrl The image url to use as the avatar.
     * @param channel The channel, if null will not be including in message request, using the default channel for the hook
     */
    public void sendMessageWithIconUrl(String text, String userName, String iconUrl, String channel) {
        Message.Builder builder = new Message.Builder(text).userName(userName).iconUrl(iconUrl).channel(channel);
        sendMessage(builder.build());
    }

    /**
     * Send a message to slack customizing username and the icon
     * @param text The message to send to slack through the web hook.
     * @param userName The username to send the message as.
     * @param iconUrl The image url to use as the avatar.
     */
    public void sendMessageWithIconUrl(String text, String userName, String iconUrl) {
        Message.Builder builder = new Message.Builder(text).userName(userName).iconUrl(iconUrl);
        sendMessage(builder.build());
    }

    /**
     * Send a message to slack customizing username and the icon
     * @param text The message to send to slack through the web hook.
     * @param userName The username to send the message as.
     * @param iconEmoji The icon emoji to use as the avatar.
     * @param channel The channel, if null will not be including in message request, using the default channel for the hook
     */
    public void sendMessageWithEmoji(String text, String userName, String iconEmoji, String channel) {
        Message.Builder builder = new Message.Builder(text).userName(userName).iconEmoji(iconEmoji).channel(channel);
        sendMessage(builder.build());
    }

    /**
     * Send a message to slack customizing username and the icon
     * @param text The message to send to slack through the web hook.
     * @param userName The username to send the message as.
     * @param iconEmoji The icon emoji to use as the avatar.
     */
    public void sendMessageWithEmoji(String text, String userName, String iconEmoji) {
        Message.Builder builder = new Message.Builder(text).userName(userName).iconEmoji(iconEmoji);
        sendMessage(builder.build());
    }

    /**
     * Send a message with complete control of what is sent to slack
     * @param message The message to send to slack
     */
    public void sendMessage(Message message) {
        try {
            client.newCall(
                    new Request.Builder()
                            .url(webHookUrl)
                            .post(RequestBody.create(JSON, message.toJson()))
                            .build()
            ).execute();
        } catch (IOException e) {
            throw new RuntimeException("Failed to communicate with Slack", e);
        }
    }

}
