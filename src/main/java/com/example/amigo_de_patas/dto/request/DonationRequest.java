package com.example.amigo_de_patas.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationRequest {

    @NotNull
    @Min(value = 100, message = "Valor deve ser maior que R$ 1,00")
    private Long donationAmount;

    private String donationCurrency = "brl";
}
