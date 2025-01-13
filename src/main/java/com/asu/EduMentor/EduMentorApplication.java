package com.asu.EduMentor;

import com.asu.EduMentor.socialMediaNotifier.EmailNotificationFacade;
import com.asu.EduMentor.socialMediaNotifier.NotificationService;
import com.asu.EduMentor.socialMediaNotifier.NotifyByEmail;
import com.asu.EduMentor.socialMediaNotifier.NotifyByFacebook;
import com.asu.EduMentor.socialMediaNotifier.NotifyByTwitter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class EduMentorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduMentorApplication.class, args);
        NotifyByFacebook notifyByFacebook = new NotifyByFacebook(NotificationService.getInstance());
        NotifyByEmail notifyByEmail = new NotifyByEmail(NotificationService.getInstance());
        NotifyByTwitter notifyByTwitter = new NotifyByTwitter(NotificationService.getInstance());
    }

    @Bean
    public CorsFilter corsFilter() throws UnirestException {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        //config.setAllowCredentials(true); // you USUALLY want this
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
