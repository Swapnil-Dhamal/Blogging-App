package com.swapnil.Blogging.App.users;

import com.swapnil.Blogging.App.common.dtos.ErrorResponse;
import com.swapnil.Blogging.App.users.dtos.CreateUserRequest;
import com.swapnil.Blogging.App.users.dtos.UserResponse;

import com.swapnil.Blogging.App.users.dtos.LoginUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> signUpUser(@RequestBody CreateUserRequest createUserRequest){
        UserEntity savedUser=userService.createUser(createUserRequest);
        URI savedUri=URI.create("/users/"+ savedUser.getId());
        return ResponseEntity.created(savedUri).body(modelMapper.map(savedUser, UserResponse.class));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest loginUserRequest){
        UserEntity user=userService.loginUser(loginUserRequest.getUsername(), loginUserRequest.getPassword());
        return ResponseEntity.ok(modelMapper.map(user, UserResponse.class));
    }

    @ExceptionHandler({UserService.UserNotFoundException.class})
    ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception e){

        String message;
        HttpStatus status;
        if(e instanceof UserService.UserNotFoundException){

            message=e.getMessage();
            status=HttpStatus.NOT_FOUND;
        }
        else{
            message="Something went wrong";
            status=HttpStatus.INTERNAL_SERVER_ERROR;
        }


        ErrorResponse response=ErrorResponse.builder()
                .message(message)
                .build();
        return ResponseEntity.status(status).body(response);
    }
}
