package com.asu.EduMentor.socialMediaNotifier;

import com.mashape.unirest.http.exceptions.UnirestException;

public class NotifyByEmail implements INotificationObserver {
    private EmailNotificationFacade emailFacade;

    public NotifyByEmail(EmailNotificationFacade emailFacade) {
        this.emailFacade = emailFacade;
    }

    public String printNotifyMethod() {
        return "Email Notification";
    }
/*In here the Facade pattern is applied because the objects of the NotifyByEmail only
interact through the update method and don't know the details of sending the email*/
    @Override
    public boolean update() throws UnirestException {

        System.out.println("Notified via Email.");
        emailFacade.sendEmail("peter", "peterpeter", "peter@elasticsdp.com");
        return true; // Indicate that the update was successful
    }
}
