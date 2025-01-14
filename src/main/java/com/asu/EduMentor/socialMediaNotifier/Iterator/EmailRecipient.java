package com.asu.EduMentor.socialMediaNotifier.Iterator;

public class EmailRecipient {
    public String subject;
    public String content;
    public String recipientEmail;

    public EmailRecipient(String subject, String content, String recipientEmail){
        this.subject = subject;
        this.content = content;
        this.recipientEmail = recipientEmail;
    }
}
