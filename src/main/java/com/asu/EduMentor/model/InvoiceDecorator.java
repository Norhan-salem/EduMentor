package com.asu.EduMentor.model;
/**
 * An abstract decorator class that extends the {@link Invoice} class and
 * serves as the base for additional decorators to add functionality to an invoice.
 * This class delegates calls to the underlying {@link Invoice} instance.
 */
public abstract class InvoiceDecorator extends Invoice {

    /**
     * The base {@link Invoice} instance being decorated.
     */
    protected Invoice invoice;

    /**
     * Constructs an InvoiceDecorator with the specified invoice to be decorated.
     *
     * @param invoice The invoice to be decorated, must not be null.
     */
    public InvoiceDecorator(Invoice invoice) {
        super(invoice.getInvoiceID());
        this.invoice = invoice;
    }

    /**
     * Returns the total amount of the decorated invoice.
     * This method delegates to the wrapped {@link Invoice} instance.
     *
     * @return The total amount of the base invoice.
     */
    @Override
    public double getTotal() {
        return invoice.getTotal();
    }
}
