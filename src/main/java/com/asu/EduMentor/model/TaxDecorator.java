package com.asu.EduMentor.model;

/**
 * A decorator class for the Invoice that adds tax to the total invoice amount.
 */
public class TaxDecorator extends InvoiceDecorator {

    private static final double TAX_RATE = 0.12;

    /**
     * Constructs a TaxDecorator with a specified base invoice and applies the static tax rate.
     *
     * @param invoice The base invoice being decorated.
     */
    public TaxDecorator(Invoice invoice) {
        super(invoice);
    }

    /**
     * Returns the total amount of the invoice including the applied tax.
     *
     * @return Total amount of the invoice with tax.
     */
    @Override
    public double getTotal() {
        double baseTotal = super.getTotal();
        return baseTotal + (baseTotal * TAX_RATE);
    }

    /**
     * Gets the tax rate applied to the invoice.
     *
     * @return The tax rate.
     */
    public static double getTaxRate() {
        return TAX_RATE;
    }
}



