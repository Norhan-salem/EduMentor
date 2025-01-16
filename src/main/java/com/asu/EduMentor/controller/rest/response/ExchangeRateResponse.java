package com.asu.EduMentor.controller.rest.response;
import java.util.Map;

public class ExchangeRateResponse {

    private Map<String, Double> conversionRates;

    public Map<String, Double> getConversionRates() {
        return conversionRates;
    }

    public void setConversionRates(Map<String, Double> conversionRates) {
        this.conversionRates = conversionRates;
    }
}
