package com.example.amigo_de_patas.service;

import com.example.amigo_de_patas.dto.request.DonationRequest;
import com.example.amigo_de_patas.dto.response.DonationResponse;
import com.example.amigo_de_patas.model.Donation;
import com.example.amigo_de_patas.model.DonationStatus;
import com.example.amigo_de_patas.repository.DonationRepository;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${app.base-url}")
    private String baseUrl;


    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Transactional
    public DonationResponse createCheckoutSession(DonationRequest request) throws StripeException {

        Stripe.apiKey = stripeSecretKey;

        Donation donation = new Donation();
        donation.setDonationAmount(request.getDonationAmount());
        donation.setDonationCurrency(request.getDonationCurrency());

        donation = donationRepository.save(donation);

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(baseUrl + "/donation/sucess")
                        .setCancelUrl(baseUrl + "/donation/cancel")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(request.getDonationCurrency())
                                                    .setUnitAmount(request.getDonationAmount())
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Doação Amigo de Patas")
                                                                    .build()
                                                    )
                                                    .build()
                                        )
                                        .build()
                        )
                        .putMetadata("donationId", donation.getDonationId().toString())
                .build();

        Session session = Session.create(params);

        donation.setStripeSessionId(session.getId());
        donationRepository.save(donation);

        return new DonationResponse(session.getUrl());
    }

    @Transactional
    public void markAsPaid(String stripeSessionId){
        Donation donation = donationRepository
                .findByStripeSessionId(stripeSessionId)
                .orElseThrow();

        donation.setDonationStatus(DonationStatus.PAID);
    }
}
