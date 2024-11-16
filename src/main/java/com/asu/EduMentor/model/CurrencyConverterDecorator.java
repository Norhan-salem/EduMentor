package com.asu.EduMentor.model;

public class CurrencyConverterDecorator extends InvoiceDecorator {
    private final double conversionRate;
    private String currency;
    private final CurrencyConversionService currencyConversionService;

    /**
     * Constructs a CurrencyConverterDecorator with a specified base invoice, target currency, and a currency conversion service.
     *
     * @param invoice                   The base invoice being decorated.
     * @param currency                  The target currency, must not be null or empty.
     * @param currencyConversionService The service responsible for fetching conversion rates.
     * @throws IllegalArgumentException if currency is null or empty.
     */
    public CurrencyConverterDecorator(Invoice invoice, String currency, CurrencyConversionService currencyConversionService) {
        super(invoice);
        setCurrency(currency);
        this.currencyConversionService = currencyConversionService;
        this.conversionRate = fetchConversionRate(currency);
    }

    /**
     * Fetches the conversion rate from the CurrencyConversionService.
     *
     * @param currency The target currency.
     * @return The conversion rate.
     */
    private double fetchConversionRate(String currency) {
        return currencyConversionService.getConversionRate(currency);
    }

    /**
     * Returns the total amount of the invoice converted into the specified currency.
     *
     * @return Total amount of the invoice in the target currency.
     */
    @Override
    public double getTotal() {
        double baseTotal = super.getTotal();
        return baseTotal * conversionRate;
    }

    /**
     * Gets the conversion rate applied to the invoice.
     *
     * @return The conversion rate.
     */
    public double getConversionRate() {
        return conversionRate;
    }

    /**
     * Gets the currency to which the invoice is converted.
     *
     * @return The target currency.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the currency to which the invoice is converted.
     *
     * @param currency The currency to set, must not be null or empty.
     * @throws IllegalArgumentException if currency is null or empty.
     */
    public void setCurrency(String currency) {
        if (currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be null or empty.");
        }
        this.currency = currency;
    }
}


