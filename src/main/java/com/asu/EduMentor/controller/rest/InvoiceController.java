package com.asu.EduMentor.controller.rest;

import com.asu.EduMentor.controller.rest.response.InvoiceResponse;
import com.asu.EduMentor.service.ICurrencyConversionService;

import com.asu.EduMentor.model.InvoiceDetails;
import com.asu.EduMentor.model.OnlineDonation;
import com.asu.EduMentor.model.Invoice;
import com.asu.EduMentor.model.invoice.decorator.CurrencyConverterDecorator;
import com.asu.EduMentor.model.invoice.decorator.TaxDecorator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final ICurrencyConversionService currencyConversionService;
    public InvoiceController(ICurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @GetMapping("/getInvoiceDetails")
    public ResponseEntity<InvoiceResponse> getInvoiceDetails(
            @RequestParam OnlineDonation donation,
            @RequestParam(required = false) String targetCurrency,
            @RequestParam(required = false, defaultValue = "true") boolean includeTax) {

        try {
            InvoiceDetails baseInvoice = new InvoiceDetails(donation.getInvoice().getInvoiceID());
            Invoice decoratedInvoice = baseInvoice;

            // Apply tax if requested
            if (includeTax) {
                decoratedInvoice = new TaxDecorator(decoratedInvoice);
            }

            if (targetCurrency != null && !targetCurrency.equals("USD")) {
                decoratedInvoice = new CurrencyConverterDecorator(
                        decoratedInvoice,
                        targetCurrency,
                        currencyConversionService
                );
            }

            InvoiceResponse response = new InvoiceResponse(
                    baseInvoice.getAmountCharged(),
                    decoratedInvoice.getTotal(),
                    targetCurrency != null ? targetCurrency : "USD"
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

