package com.asu.EduMentor.socialMediaNotifier;

import com.asu.EduMentor.socialMediaNotifier.Iterator.*;
import com.asu.EduMentor.socialMediaNotifier.Iterator.Iterable;

public class NotifyByEmail implements INotificationObserver {
    private EmailNotificationFacade emailFacade;

    Iterable emailRecipientCollection;

    public NotifyByEmail(NotificationService notificationService, EmailNotificationFacade emailFacade) {
        this.emailFacade = emailFacade;
        notificationService.addObserver(this);
        this.emailRecipientCollection = new EmailRecipientCollection();
    }

    public String printNotifyMethod() {
        return "Email Notification";
    }
/*In here the Facade pattern is applied because the objects of the NotifyByEmail only
interact through the update method and don't know the details of sending the email*/
    @Override
    public boolean update(){
        try {
            Iterator emailRecipientsIterators = emailRecipientCollection.createIterator();
            while (emailRecipientsIterators.hasNext()){
                EmailRecipient recipient = (EmailRecipient) emailRecipientsIterators.next();
                emailFacade.sendEmail(recipient.subject, recipient.content, recipient.recipientEmail);
            }

            return true; // Indicate that the update was successful
        } catch (Exception e) {
            return false;
        }
    }
}
