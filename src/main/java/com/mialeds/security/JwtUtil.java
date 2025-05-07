package com.mialeds.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    
    private final String LLAVE_SECRETA = "tU4Fe2bD6d6N4XW9mTehRYM1S1CV0rl+gZlAjMJ2pgU=";
    private final long TIEMPO_EXPIRACION = 86400000; // 1 día en milisegundos

    private Key getLlave() {
        return Keys.hmacShaKeyFor(LLAVE_SECRETA.getBytes());
    }

    public String generarToken(String usuario) {
        return Jwts.builder()
            .setSubject(usuario)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + TIEMPO_EXPIRACION))
            .signWith(getLlave(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String extraerUsuario(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getLlave())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public Boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getLlave()).build().parseClaimsJws(token);
            return true;
        }catch (JwtException e) {
            return false;
        }
    }
}
