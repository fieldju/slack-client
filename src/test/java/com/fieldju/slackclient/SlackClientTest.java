package com.fieldju.slackclient;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SlackClientTest {

    private SlackClient client;
    private MockWebServer server;
    HttpUrl baseUrl;

    @Before
    public void before() {
        server = new MockWebServer();
        baseUrl = server.url("/fake-api-endpoint");
        client = new SlackClient(baseUrl.toString());
    }

    @After
    public void after() throws IOException {
        server.shutdown();
    }

    @Test
    public void test_that_send_message_posts_to_slack() throws InterruptedException {
        server.enqueue(new MockResponse().setResponseCode(200));
        client.sendMessage("Hello World");

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("/fake-api-endpoint", recordedRequest.getPath());
        assertEquals("POST", recordedRequest.getMethod());
    }

}
