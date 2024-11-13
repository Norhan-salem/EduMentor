package com.asu.EduMentor;

import com.asu.EduMentor.controller.rest.ICurrencyConversionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CurrencyConversionServiceIntegrationTest {

    @Autowired
    private ICurrencyConversionService currencyConversionService;

    @Test
    public void testFetchConversionRateFromAPI() {
        double conversionRate = currencyConversionService.getConversionRate("GBP");
        assertTrue(conversionRate > 0);
    }
}
