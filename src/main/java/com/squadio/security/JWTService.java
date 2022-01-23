package com.squadio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
@Service
public class JWTService {
    @Value("${security.jwt.token.issuer:squadio}")
    private String issuer;

    private static final GzipCompressionCodec COMPRESSION_CODEC = new GzipCompressionCodec();

    public String permanent(Map<String, String> attributes, String secretKey) {
        return newToken(attributes, secretKey, 0);
    }

    public String expiringToken(Map<String, String> attributes, String secretKey, int minutes) {
        return newToken(attributes, secretKey, minutes);
    }

    public Map<String, String> verify(String token, String secretKey) {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        JwtParser parser = Jwts
                .parser()
                .requireIssuer(issuer)
                .setSigningKey(secretKey);
        return parseClaims(() -> parser.parseClaimsJws(token).getBody());
    }

    private String newToken(final Map<String, String> attributes, String secretKey, final int expiresInMin) {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        DateTime now = DateTime.now();
        final Claims claims = Jwts
                .claims()
                .setIssuer(issuer)
                .setIssuedAt(now.toDate());

        if (expiresInMin > 0) {
            final DateTime expiresAt = now.plusMinutes(expiresInMin);
            claims.setExpiration(expiresAt.toDate());
        }
        claims.putAll(attributes);
        return Jwts
                .builder()
                .setClaims(claims)
                .signWith(HS256, secretKey)
                .compressWith(COMPRESSION_CODEC)
                .compact();
    }

    private static Map<String, String> parseClaims(final Supplier<Claims> toClaims) {
        Map<String, String> stringMap = new HashMap<>();
        try {
            final Claims claims = toClaims.get();
            for (final Map.Entry<String, Object> e: claims.entrySet()) {
                stringMap.put(e.getKey(), String.valueOf(e.getValue()));
            }
            return stringMap;
        } catch (final IllegalArgumentException | JwtException e) {
            return stringMap;
        }
    }
}
