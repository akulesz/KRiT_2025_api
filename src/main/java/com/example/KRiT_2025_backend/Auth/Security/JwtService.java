package com.example.KRiT_2025_backend.Auth.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String SECRET_KEY = "tajny_klucz_jwt_ktory_powinien_byc_dlugi_i_trzymany_bezpiecznie";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60); // 1h

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(algorithm);
    }
    public String extractUsername(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build(); // Weryfikator JWT
        DecodedJWT jwt = verifier.verify(token);               // Weryfikuj i dekoduj token
        return jwt.getSubject();                               // Zwróć subject, czyli username
    }
}
