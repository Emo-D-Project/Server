package com.mydiary.my_diary_server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class FCMConfig {
    private static final Logger logger = LoggerFactory.getLogger(FCMConfig.class);

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        ClassPathResource resource = new ClassPathResource("firebase/emodsujin-firebase-adminsdk-h85dm-1a11d02924.json");

        InputStream refreshToken = resource.getInputStream();
        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();

        if(firebaseAppList != null && !firebaseAppList.isEmpty()){
            for(FirebaseApp app : firebaseAppList){
                if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
                    firebaseApp = app;
                }
            }
        }   else {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setProjectId("647782774462")
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        }


        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance(firebaseApp);
        logger.info("FirebaseMessaging initialized: {}", firebaseMessaging.toString());
        return firebaseMessaging;

    }
}
