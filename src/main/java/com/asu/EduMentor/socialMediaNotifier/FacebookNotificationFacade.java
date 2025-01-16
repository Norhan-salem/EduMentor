package com.asu.EduMentor.socialMediaNotifier;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FacebookNotificationFacade {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String PAGE_ACCESS_TOKEN = dotenv.get("FACEBOOKAPIKEY");
    private static final String PAGE_ID = dotenv.get("FACEBOOKPAGEID");;

    public boolean createPost(String content) throws Exception {
        String endpoint = "https://graph.facebook.com/v21.0/" + PAGE_ID + "/feed";

        // Create the POST payload
        String payload = "message=" + content + "&access_token=" + PAGE_ACCESS_TOKEN;

        // Open connection
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        // Send the payload
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = payload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Read the response
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            return true;
        } else {
            return false;
        }
    }
}

