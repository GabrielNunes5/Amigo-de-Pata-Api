package com.example.amigo_de_patas.dto.response;

public class DonationResponse {

    private final String checkoutUrl;

    public DonationResponse(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }
}
