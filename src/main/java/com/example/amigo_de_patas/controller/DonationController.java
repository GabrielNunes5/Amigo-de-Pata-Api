package com.example.amigo_de_patas.controller;

import com.example.amigo_de_patas.dto.request.DonationRequest;
import com.example.amigo_de_patas.dto.response.DonationResponse;
import com.example.amigo_de_patas.service.DonationService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/donation")
public class DonationController {
    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public ResponseEntity<DonationResponse> createDonation(
            @Valid @RequestBody DonationRequest donationRequest
    ) throws StripeException {
        DonationResponse response = donationService.createCheckoutSession(donationRequest);
        return ResponseEntity.ok(response);
    }

}
