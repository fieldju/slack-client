package com.fieldju.slackclient;

public class Message {

    private final String text;
    private final String channel;
    private final String iconEmoji;
    private final String iconUrl;
    private final String userName;

    /**
     *
     * Constructs the json message to post to the Slack Web Hook.
     * If emoji icon and icon url are set, the emoji icon will be used and the icon url will not be part of the json
     *
     * @return json string of this message
     */
    public String toJson() {
        StringBuilder builder = new StringBuilder("{");

        builder.append("\"text\":\"").append(text).append("\",");

        if (channel != null) {
            builder.append("\"channel\":\"").append(channel).append("\",");
        }

        if (userName != null) {
            builder.append("\"username\":\"").append(userName).append("\",");
        }

        if (iconEmoji != null) {
            builder.append("\"icon_emoji\":\"").append(iconEmoji).append("\"");
        }

        if (iconUrl != null && iconEmoji == null) {
            builder.append("\"icon_url\":\"").append(iconUrl).append("\"");
        }

        builder.append("}");
        return builder.toString();
    }

    private Message(Builder builder) {
        this.text = builder.text;
        this.channel = builder.channel;
        this.iconEmoji = builder.iconEmoji;
        this.iconUrl = builder.iconUrl;
        this.userName = builder.userName;
    }

    public static class Builder {

        private String text;
        private String channel;
        private String iconEmoji;
        private String iconUrl;
        private String userName;

        public Builder(String text) {
            this.text = text;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder channel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder iconEmoji(String iconEmoji) {
            this.iconEmoji = iconEmoji;
            return this;
        }

        public Builder iconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
