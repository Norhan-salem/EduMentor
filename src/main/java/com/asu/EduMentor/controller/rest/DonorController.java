package com.asu.EduMentor.controller.rest;

import com.asu.EduMentor.controller.rest.paymentProcessor.strategy.*;
import com.asu.EduMentor.logging.DonationLog;
import com.asu.EduMentor.logging.LoggingMediator;
import com.asu.EduMentor.model.OnlineDonation;
import com.asu.EduMentor.model.OnlineDonor;
import com.asu.EduMentor.model.PaymentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donor")
public class DonorController {

    @PostMapping("/api/makeDonation")
    public ResponseEntity<CreatePaymentResponse> makeDonation(@RequestBody DonationRequest donorDonationRequest) {
        int donorId = donorDonationRequest.getDonorId();
        OnlineDonor donor = (OnlineDonor) new OnlineDonor().read(donorId);

//        OnlineDonor donor = new OnlineDonor(donorDonationRequest.getFirstName(), donorDonationRequest.getLastName(), donorDonationRequest.getEmail());

        PaymentType paymentType = PaymentType.VISA;
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        if (donorDonationRequest.getPaymentType().equals("Card")){
            paymentProcessor.setPaymentStrategy(new VisaPaymentStrategy());
        }else if(donorDonationRequest.getPaymentType().equals("Courier")){
            paymentType = PaymentType.COURIER;
            paymentProcessor.setPaymentStrategy(new CourierPaymentStrategy());
        }

        OnlineDonation donation = new OnlineDonation(paymentType, (double) donorDonationRequest.getAmount());

        String response = paymentProcessor.executePayment(donorDonationRequest.getAmount() * 100);

        try {
            if (!donation.makeDonation(donor, paymentType)) {
                throw new Exception("Make donation failed");
            }
            LoggingMediator.getInstance().log(new DonationLog(donor.getFirstName(), donation.getAmount()));
            return ResponseEntity.ok(new CreatePaymentResponse(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CreatePaymentResponse(e.getMessage()));
        }
    }

    @GetMapping("/api/getDonations")
    public ResponseEntity<List<OnlineDonation>> getDonations(@RequestParam OnlineDonor donor) {
        try {
            return ResponseEntity.ok(donor.getDonations());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
}
