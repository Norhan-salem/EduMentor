package com.asu.EduMentor;

import com.asu.EduMentor.logging.LoggingThread;
import com.asu.EduMentor.socialMediaNotifier.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EduMentorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduMentorApplication.class, args);
        NotifyByFacebook notifyByFacebook = new NotifyByFacebook(NotificationService.getInstance());
        NotifyByEmail notifyByEmail = new NotifyByEmail(NotificationService.getInstance(), new EmailNotificationFacade());
        NotifyByTwitter notifyByTwitter = new NotifyByTwitter(NotificationService.getInstance());

        // start the logging thread
        LoggingThread loggingThread = new LoggingThread();
        new Thread(loggingThread).start();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
