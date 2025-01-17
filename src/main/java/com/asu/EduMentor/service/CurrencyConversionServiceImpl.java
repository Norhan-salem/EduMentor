package com.asu.EduMentor.service;

import com.asu.EduMentor.controller.rest.response.ExchangeRateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


@Service
public class CurrencyConversionServiceImpl implements ICurrencyConversionService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionServiceImpl.class);
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/da762ace32b2903e9653ddf5/latest/USD";

    private final RestTemplate restTemplate;

    public CurrencyConversionServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public double getConversionRate(String targetCurrency) {
        try {
            logger.info("Fetching conversion rate for currency: {}", targetCurrency);

            ExchangeRateResponse response = restTemplate.getForObject(API_URL, ExchangeRateResponse.class);
            logger.info("Raw Response: {}", response);

            if (response != null && response.getConversionRates() != null) {
                Double rate = response.getConversionRates().get(targetCurrency);
                logger.info("Retrieved rate for {}: {}", targetCurrency, rate);

                if (rate != null) {
                    return rate;
                } else {
                    logger.warn("Rate not found for currency: {}. Defaulting to 1.0", targetCurrency);
                }
            } else {
                logger.warn("Response or conversion rates were null. Defaulting to 1.0");
            }

            return 1.0;
        } catch (RestClientException e) {
            logger.error("Error fetching conversion rates", e);
            throw new RuntimeException("Error fetching conversion rates", e);
        }
    }
}



