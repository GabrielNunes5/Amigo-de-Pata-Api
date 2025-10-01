package com.example.amigo_de_patas.dto.response;

import com.example.amigo_de_patas.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long expiresIn;
    private String adopterFullName;
    private String adopterEmail;
    private Role role;
}
