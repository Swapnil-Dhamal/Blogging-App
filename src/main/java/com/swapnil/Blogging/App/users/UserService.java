package com.swapnil.Blogging.App.users;

import com.swapnil.Blogging.App.users.dtos.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepo userRepo, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(CreateUserRequest req) {

        UserEntity newUser=modelMapper.map(req, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
//        var newUser = UserEntity.builder()
//                .username(req.getUsername())
////                .password(password)
//                .email(req.getEmail())
//                .build();

        return userRepo.save(newUser);

    }
    public UserEntity getUserByUsername(String username){
        return userRepo.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
    }

    public UserEntity getUserByUserID(Long userId){
        return userRepo.findById(userId).orElseThrow(()->new UserNotFoundException(userId) );
    }

    public UserEntity loginUser(String username, String password){
        var user =userRepo.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username));

        var passMatch=passwordEncoder.matches(password, user.getPassword());

        if(!passMatch){
            throw new InvalidCredentialsException();
        }

        return user;
    }



    public void registerUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Store encoded password
        userRepo.save(user);
    }


    public static class UserNotFoundException extends IllegalArgumentException{

        public UserNotFoundException(String username) {
            super("User with "+username+" not found");
        }

        public UserNotFoundException(Long authorId){
            super("User with "+authorId+" not found");
        }
    }

    public static class InvalidCredentialsException extends  IllegalArgumentException{
        public InvalidCredentialsException(){
            super("Invalid username or password");
        }

    }


}
