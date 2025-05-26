package com.example.KRiT_2025_backend.Auth.Security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class JwtResponse {
    private String token;
}
