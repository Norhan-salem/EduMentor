package com.asu.EduMentor.model.invoice.decorator;

import com.asu.EduMentor.service.CurrencyConversionServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConversionRunner implements CommandLineRunner {

    private final CurrencyConversionServiceImpl service;

    public CurrencyConversionRunner(CurrencyConversionServiceImpl service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {
        System.out.println("Testing Currency Conversion Service...");

        String[] testCurrencies = {"EUR", "EGP", "XYZ"};

        for (String currency : testCurrencies) {
            double rate = service.getConversionRate(currency);
            System.out.println("Rate for " + currency + ": " + rate);
        }
    }
}
