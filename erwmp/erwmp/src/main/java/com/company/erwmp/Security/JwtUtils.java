package com.company.erwmp.Security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")  //Reads the secret from application.properties, used to sign in and verify tokens
    private String jwtSecret;

    @Value("${jwt.expiration-ms}") //reads from application.properties, //Token validity duration (e.g., 1 hour)
    private long jwtExpirationMs;

    public String generateToken(String username) {  //to create a JWT after sucessful login
        return Jwts.builder()  //token builder
                .setSubject(username)  //stores username inside a token
                .setIssuedAt(new Date())  //token created date
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))  //set expiration
                .signWith(SignatureAlgorithm.HS256, jwtSecret)   //Signs token with secret key
                .compact();         //Converts everything into a single token string
    }

    public String getUsernameFromToken(String token) {     //extracts username from token
        return Jwts.parser()
                .setSigningKey(jwtSecret) //Uses secret to verify token authenticity
                .parseClaimsJws(token) //Parses and validates the token
                .getBody()  //Reads username from token payload
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
           Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);  //If parsing succeeds → token is valid
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

/*
* JwtUtils is like a Security Office that:
Creates ID cards (JWT tokens)
Reads ID cards (extracts username)
*
*
* */