package com.swapnil.Blogging.App.users;

import com.swapnil.Blogging.App.common.dtos.ErrorResponse;
import com.swapnil.Blogging.App.security.JWTService;
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
    private final JWTService jwtService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, JWTService jwtService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> signUpUser(@RequestBody CreateUserRequest createUserRequest){
        UserEntity savedUser=userService.createUser(createUserRequest);
        URI savedUri=URI.create("/users/"+ savedUser.getId());
        var savedUserResponse=modelMapper.map(savedUser, UserResponse.class);
        savedUserResponse.setToken(
                jwtService.createJwt(savedUser.getId())
        );

        return ResponseEntity.created(savedUri).body(savedUserResponse);

    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest loginUserRequest){
        UserEntity user=userService.loginUser(loginUserRequest.getUsername(), loginUserRequest.getPassword());
        var savedUserResponse=modelMapper.map(user, UserResponse.class);
        savedUserResponse.setToken(
                jwtService.createJwt(savedUserResponse.getId())
        );
        return ResponseEntity.ok(savedUserResponse);
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
