package co.edu.ustavillavicencio.comeya.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(expirationMs)))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getSubject(String token) {
        try {
            // Basic JWT structure validation and HMAC-SHA256 signature check
            String[] parts = token.split("\\.");
            if (parts.length != 3) return null;
            String headerB64 = parts[0];
            String payloadB64 = parts[1];
            String sigB64 = parts[2];

            String signingInput = headerB64 + "." + payloadB64;

            byte[] secret = secretKey.getBytes(StandardCharsets.UTF_8);
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
            mac.init(new javax.crypto.spec.SecretKeySpec(secret, "HmacSHA256"));
            byte[] expected = mac.doFinal(signingInput.getBytes(StandardCharsets.US_ASCII));

            java.util.Base64.Encoder enc = java.util.Base64.getUrlEncoder().withoutPadding();
            String expectedB64 = enc.encodeToString(expected);
            if (!java.security.MessageDigest.isEqual(expectedB64.getBytes(StandardCharsets.US_ASCII), sigB64.getBytes(StandardCharsets.US_ASCII))) {
                return null;
            }

            java.util.Base64.Decoder dec = java.util.Base64.getUrlDecoder();
            String payloadJson = new String(dec.decode(payloadB64), StandardCharsets.UTF_8);
            com.fasterxml.jackson.databind.JsonNode node = new com.fasterxml.jackson.databind.ObjectMapper().readTree(payloadJson);
            return node.has("sub") ? node.get("sub").asText() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
