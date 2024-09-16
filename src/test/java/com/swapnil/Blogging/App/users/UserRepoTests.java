package com.swapnil.Blogging.App.users;



import lombok.Builder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest

@ActiveProfiles("test")
public class UserRepoTests {

    @Autowired
    private UserRepo userRepo;

    @Test
    @Order(1)
    void can_create_user(){
        var user=UserEntity.builder()
                .username("Swapnil")
                .email("swapnil123@gmail.com")
                .build();

        userRepo.save(user);

    }

    @Test
    @Order(2)
    void can_find_user(){

        var user=UserEntity.builder()
                .username("Swapnil")
                .email("swapnil123@gmail.com")
                .build();

        userRepo.save(user);
        var users=userRepo.findAll();
        Assertions.assertEquals(1, users.size());
    }
}
