package com.asu.EduMentor.socialMediaNotifier;

public class NotifyByEmail implements INotificationObserver {
    private EmailNotificationFacade emailFacade;

    public NotifyByEmail(NotificationService notificationService, EmailNotificationFacade emailFacade) {
        this.emailFacade = emailFacade;
        notificationService.addObserver(this);
    }

    public String printNotifyMethod() {
        return "Email Notification";
    }
/*In here the Facade pattern is applied because the objects of the NotifyByEmail only
interact through the update method and don't know the details of sending the email*/
    @Override
    public boolean update(){
        try {
            // TODO: there should be a list of recipients
            emailFacade.sendEmail("peter", "peterpeter", "peter@elasticsdp.com");

            return true; // Indicate that the update was successful
        } catch (Exception e) {
            return false;
        }
    }
}
