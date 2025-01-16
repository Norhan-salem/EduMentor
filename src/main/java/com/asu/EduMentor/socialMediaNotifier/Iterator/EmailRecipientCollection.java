package com.asu.EduMentor.socialMediaNotifier.Iterator;

import java.util.ArrayList;

public class EmailRecipientCollection implements Iterable{

    ArrayList<EmailRecipient> recipients;

    public EmailRecipientCollection(){
        recipients = new ArrayList<EmailRecipient>(); // TODO: fetch them from database
        recipients.add(new EmailRecipient("New Session", "peterpeter", "peter@elasticsdp.com"));
    }

    @Override
    public Iterator createIterator() {
        return new EmailRecipientsIterator(this.recipients);
    }
}
