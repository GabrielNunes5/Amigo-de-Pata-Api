package com.example.amigo_de_patas.dto.request;

import com.example.amigo_de_patas.model.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequest {
    @NotNull
    private Status status;
}