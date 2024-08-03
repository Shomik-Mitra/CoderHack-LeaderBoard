package com.coderhack.leaderboard.controller;

import com.coderhack.leaderboard.model.dto.UserDTO;
import com.coderhack.leaderboard.model.dto.UserRegiatrationDTO;
import com.coderhack.leaderboard.model.dto.UserScoreUpdateDTO;
import com.coderhack.leaderboard.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/users";
    public static final String USER_ID_PATH = "/{userId}";

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserRegiatrationDTO> registerUser(@Valid @RequestBody UserRegiatrationDTO userRegiatrationDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRegiatrationDTO));
    }

    @GetMapping
    public  ResponseEntity<List<UserDTO>> getAllRegisterUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(USER_ID_PATH)
    public ResponseEntity<UserDTO> getSpecificUser(@PathVariable("userId") String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping(USER_ID_PATH)
    public ResponseEntity<UserDTO> updateScoreForSpecificUser(@PathVariable("userId") String id, @Valid @RequestBody UserScoreUpdateDTO userScoreUpdateDTO){
        return ResponseEntity.ok(userService.updateUserScore(id,userScoreUpdateDTO));
    }

    @DeleteMapping(USER_ID_PATH)
    public ResponseEntity<Void> deregisterUser(@PathVariable("userId") String id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
