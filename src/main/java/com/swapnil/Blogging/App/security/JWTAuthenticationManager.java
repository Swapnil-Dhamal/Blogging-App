package com.swapnil.Blogging.App.security;

import com.swapnil.Blogging.App.users.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationManager implements AuthenticationManager {

    private JWTService jwtService;
    private UserService userService;

    public JWTAuthenticationManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if(authentication instanceof JWTAuthentication){
            var jwtAuthentication=(JWTAuthentication)authentication;
            var jwt=jwtAuthentication.getCredentials();

            if(jwt instanceof String){
                String token=(String) jwt;
                var userId=jwtService.retrieveUser(token);
            }
            else{
                throw new AuthenticationException("Invalid JWT token format") {
                };
            }

        }
        return null;
    }
}
