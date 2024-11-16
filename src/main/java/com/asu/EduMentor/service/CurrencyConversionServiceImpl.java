package com.asu.EduMentor.service;

import com.asu.EduMentor.controller.rest.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

@Service
public class CurrencyConversionServiceImpl implements ICurrencyConversionService {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/da762ace32b2903e9653ddf5/latest/USD";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public double getConversionRate(String targetCurrency) {
        try {
            // Call the API to get conversion rates from USD
            ExchangeRateResponse response = restTemplate.getForObject(API_URL, ExchangeRateResponse.class);

            if (response != null && response.getConversionRates() != null) {
                Double rate = response.getConversionRates().get(targetCurrency);
                if (rate != null) {
                    return rate;
                }
            }

            return 1.0;
        } catch (RestClientException e) {
            throw new RuntimeException("Error fetching conversion rates", e);
        }
    }
}

