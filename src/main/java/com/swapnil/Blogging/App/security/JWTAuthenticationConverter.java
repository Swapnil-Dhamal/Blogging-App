package com.swapnil.Blogging.App.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

public class JWTAuthenticationConverter implements AuthenticationConverter {


    @Override
    public Authentication convert(HttpServletRequest request) {

        var authHeader=request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("bearer ")){
            return null;
        }

        var jwt=authHeader.substring(7);
        return new JWTAuthentication(jwt);
    }
}
