package com.example.microserviciousuario.servicios;

import com.example.microserviciousuario.entidades.Rol;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtTokenProvider {

    @Value("${token.jwt}")
    private String secretKey;
    //private String secretKey = "miClaveSecreta";

    public String createToken(String username, int idUser, List<Rol> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("idUser", idUser);
        claims.put("roles", roles.stream().map(Rol::getRole).collect(Collectors.toList()));

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hora de validez

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
