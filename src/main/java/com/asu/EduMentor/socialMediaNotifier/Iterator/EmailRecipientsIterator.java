package com.asu.EduMentor.socialMediaNotifier.Iterator;

import java.util.ArrayList;

public class EmailRecipientsIterator implements Iterator{

    ArrayList<EmailRecipient> recipients;
    int position = 0;

    public EmailRecipientsIterator(ArrayList<EmailRecipient> recipients){
        this.recipients = recipients;
    }

    @Override
    public Object next() {
        return recipients.get(position++);
    }

    @Override
    public boolean hasNext() {
        return recipients.size() > (position);
    }
}
