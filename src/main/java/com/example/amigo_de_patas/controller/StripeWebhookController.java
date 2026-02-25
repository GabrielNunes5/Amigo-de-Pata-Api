package com.example.amigo_de_patas.controller;

import com.example.amigo_de_patas.service.DonationService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donation/webhook")
public class StripeWebhookController {

    @Value("${stripe.webhook.key}")
    private String endpointSecret;

    private final DonationService donationService;

    public StripeWebhookController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ){
        Event event;

        try{
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        if ("checkout.session.completed".equals(event.getType())) {

            Session session = (Session) event.getDataObjectDeserializer()
                    .getObject()
                    .orElse(null);

            if(session != null) {
                donationService.markAsPaid(session.getId());
            }
        }

        return ResponseEntity.ok().build();
    }
}
