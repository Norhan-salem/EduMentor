package com.asu.EduMentor.controller.rest;

import com.asu.EduMentor.controller.rest.body.DonorDonationRequest;
import com.asu.EduMentor.model.Invoice;
import com.asu.EduMentor.model.InvoiceDetails;
import com.asu.EduMentor.model.OnlineDonation;
import com.asu.EduMentor.model.OnlineDonor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donor")
public class DonorController {

    @PostMapping("/api/makeDonation")
    public ResponseEntity<Boolean> makeDonation(@RequestBody DonorDonationRequest donorDonationRequest){
        OnlineDonation donation = donorDonationRequest.getOnlineDonation();
        OnlineDonor donor = donorDonationRequest.getOnlineDonor();

        try {
            if (!donation.makeDonation(donor)){
                throw new Exception("Make donation failed");
            }
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @GetMapping("/api/getDonations")
    public ResponseEntity<List<OnlineDonation>> getDonations(@RequestParam OnlineDonor donor){
        try {
            return ResponseEntity.ok(donor.getDonations());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }

    @GetMapping("/api/getInvoiceDetails")
    public ResponseEntity<InvoiceDetails> getInvoiceDetails(@RequestParam OnlineDonation donation){
        InvoiceDetails invoiceDetails = new InvoiceDetails(donation.getInvoice().getInvoiceID());
        try {
            return ResponseEntity.ok(invoiceDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
