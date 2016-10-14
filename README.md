# Slack Client
Simple client for sending messages to a slack webhook url in the JVM

    SlackClient client = new SlackClient("https://hooks.slack.com/services/abc123/cdf123/asdfasdfasdf");
    client.sendMessage("Hello");
    client.sendMessage("Hello Again", "custom-username", ":dog:");
    client.setUsername("custom-username")
    client.sendMessage("Hello Again Again");