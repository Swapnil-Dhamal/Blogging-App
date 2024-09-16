package com.swapnil.Blogging.App.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    private static final String JWT_KEY="HBIHYCDG*Yhibyibyt77665456";
    private final Algorithm algorithm=Algorithm.HMAC256(JWT_KEY);

    public String createJwt(Long userId){
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public Long retrieveUser(String jwtString){
        var decodedJwt=JWT.decode(jwtString);
        var userId=Long.valueOf(decodedJwt.getSubject());
        return userId;
    }
}
