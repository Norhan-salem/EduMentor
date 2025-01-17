package com.asu.EduMentor.controller.rest.paymentProcessor.strategy;

import com.asu.EduMentor.socialMediaNotifier.EmailNotificationFacade;

public class CourierPaymentStrategy implements IPaymentStrategy {

    @Override
    public String processPayment(double amount) {
        (new EmailNotificationFacade()).sendEmail("New Courier donation",
                "A new courier donation is done with amount " +
                        amount, "minassgerges@gmail.com");

        return "";
    }
}