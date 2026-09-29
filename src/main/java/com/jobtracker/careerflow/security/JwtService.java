package com.jobtracker.careerflow.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String email){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder().subject(email).issuedAt(now).expiration(expiryDate).signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                secret))).compact();
    }

    public String extractEmail(String token){

        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        Jws<Claims> jws;

        jws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);

        Claims payload = jws.getPayload();

        return payload.getSubject();
    }
}