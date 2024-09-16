package com.swapnil.Blogging.App.users;

import com.swapnil.Blogging.App.security.JWTService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JWTServiceTest {

    JWTService jwtService=new JWTService();

    @Test
    void canCreateJwtFromUserId(){
        var jwt=jwtService.createJwt(6786L);
        assertNotNull(jwt);
    }
}
