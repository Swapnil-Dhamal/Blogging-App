package com.swapnil.Blogging.App.users;

import com.swapnil.Blogging.App.users.dtos.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;


    @Autowired
    public UserService(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    public UserEntity createUser(CreateUserRequest req) {

        UserEntity newUser=modelMapper.map(req, UserEntity.class);
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
