package com.asu.EduMentor.socialMediaNotifier.Iterator;

import com.asu.EduMentor.model.Mentee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EmailRecipientCollection implements Iterable{

    private static final Logger log = LoggerFactory.getLogger(EmailRecipientCollection.class);
    ArrayList<EmailRecipient> recipients;
    private static final String NEW_SESSION_SUBJECT = "New Session Email";


    public EmailRecipientCollection(){
        recipients = new ArrayList<EmailRecipient>();
        initializeRecipients();
    }

    @Override
    public Iterator createIterator() {
        return new EmailRecipientsIterator(this.recipients);
    }

    private void initializeRecipients() {
        try {
            List<Object> mentees = new Mentee().readAll();

            for (Object menteeObj : mentees) {
                if (menteeObj instanceof Mentee) {
                    Mentee mentee = (Mentee) menteeObj;
                    String menteeEmail = mentee.getEmail();
                    if (menteeEmail != null && !menteeEmail.isEmpty()) {
                        recipients.add(new EmailRecipient(NEW_SESSION_SUBJECT, menteeEmail));
                        log.info("Added recipient: " + menteeEmail);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error initializing recipients: " + e.getMessage());
        }
    }
}
