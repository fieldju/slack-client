 [ ![Download](https://api.bintray.com/packages/fieldju/maven/slack-client/images/download.svg) ](https://bintray.com/fieldju/maven/slack-client/_latestVersion) [![Build Status](https://travis-ci.org/fieldju/slack-client.svg?branch=master)](https://travis-ci.org/fieldju/slack-client) [![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/fieldju/slack-client/master/LICENSE)
 
# Slack Client
Simple client for sending messages to a slack webhook url in the JVM

    SlackClient client = new SlackClient("https://hooks.slack.com/services/abc123/cdf123/asdfasdfasdf");
    client.sendMessage("Hello");
    client.sendMessage("Hello Again", "custom-username", ":dog:");
    client.setUsername("custom-username")
    client.sendMessage("Hello Again Again");
