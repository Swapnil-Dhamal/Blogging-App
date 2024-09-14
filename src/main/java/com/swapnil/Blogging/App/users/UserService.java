package com.swapnil.Blogging.App.users;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserEntity createUser(String username, String password, String email) {
        var newUser = UserEntity.builder()
                .username(username)
//                .password(password)
                .email(email)
                .build();

        return userRepo.save(newUser);

    }
    public UserEntity getUserByUsername(String username){
        return userRepo.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
    }

    public UserEntity getUserByUserID(Long userId){
        return userRepo.findByUserId(userId).orElseThrow(()->new UserNotFoundException(userId) );
    }

    public UserEntity loginUser(String username, String password){
        var user =userRepo.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username));


        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException{

        public UserNotFoundException(String username) {
            super("User with "+username+" not found");
        }

        public UserNotFoundException(Long authorId){
            super("User with "+authorId+" not found");
        }
    }


}
