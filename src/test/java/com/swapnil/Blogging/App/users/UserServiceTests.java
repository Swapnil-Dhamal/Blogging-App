package com.swapnil.Blogging.App.users;

import com.swapnil.Blogging.App.users.dtos.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Test
    void can_create_user() {
        var user = userService.createUser(
                new CreateUserRequest(
                        "Dhamal",
                        "D123",
                        "dhamal2@gmail.com"
                )
        );

        Assertions.assertNotNull(user);
        Assertions.assertEquals("Dhamal", user.getUsername());
    }

}
